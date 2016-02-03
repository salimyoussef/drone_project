import endPoints.AdressEndPoints;
import maps.GoogleMap;
import maps.MapIF;
import path.Path;
import path.PathPoint;
import pathFinder.GooglePathFinder;
import pathFinder.PathPlannerStrategy;
import pathToNavCommands.CommandsProvider;
import tracer.Tracer;
import utils.MyConstants;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NTracerSimulation {

    public static void main(String args[]) {
        int n = MyConstants.NUMBER_OF_DRONES;
        TracerTask[] t = new TracerTask[n];
        for (int i = 0; i < n; i++) {
            t[i] = new NTracerSimulation.TracerTask(i);
            Thread tr = new Thread(t[i]);
            tr.start();
        }
        Scanner sc = new Scanner(System.in);
        String rep = sc.next();
        if(rep.equals("start")){
            for(int i = 0; i < n; i++){
               synchronized(t[i]){
                   t[i].notifyAll();
               }
           }
        }
    }

    static class TracerTask implements Runnable {

        int id;

        public TracerTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
                //int nb_topics_per_zookeeper = MyConstants.NUMBER_OF_DRONES/MyConstants.NUMBER_OF_ZOOKEEPER;
            Random genetator = new Random();
            String dest[] = {"Dijon", "Lille", "Rennes", "Tours", "Caen", "Nantes", "Angers"};
             Random random = new Random();
            int idx1 = random.nextInt(7);
            String destination = dest[idx1];
            //String destination = "Paris";
            int idx2 = random.nextInt(7);
                if (idx2 == idx1) {
                    idx2 = (idx2 + 1) % dest.length;
                }

            String start = dest[idx2];
            //String start = "Lille";
            PathPlannerStrategy pathPlanner = new GooglePathFinder();
            System.out.println(start + ":" + destination);
            Path p = pathPlanner.findPath(new AdressEndPoints(start, destination));

            /*ArrayList<PathPoint> path = new ArrayList<>();
            for(int i=0; i<30;i++){
                path.add(new PathPoint(10+i,20,12));
            }*/

            CommandsProvider provider = new CommandsProvider("drone"+id);
            provider.setPath(p.getPathPoints());
            provider.sendCommands();

            try {
                synchronized (this){
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            provider.sendGoCommand();

            Tracer tracer = new Tracer("tracer" + id, "drone" + id, "localhost:" + MyConstants.KAFKA_ZK_PORT); //+ (id/nb_topics_per_zookeeper)));
            MapIF map = null;
            try {
                map = new GoogleMap(p);
                tracer.setMap(map);
                map.paintMap(p);
                tracer.run(1);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

    }
}