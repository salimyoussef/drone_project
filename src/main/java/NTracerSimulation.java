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

import communicator.Communicator;

public class NTracerSimulation {
    public static void main(String args[]) {
        int n = MyConstants.NUMBER_OF_DRONES;
        for (int i = 0; i < n; i++) {
            Thread t = new Thread(new NTracerSimulation.TracerTask(i));
            t.start();
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
            //String drone = EventMediatorLocator.mediator().allocateDrone();
            //String dest[] = {"Dijon", "Lille", "Rennes", "Tours", "Caen", "Nantes", "Angers"};
            // Random random = new Random();
            //int idx1 = random.nextInt(7);
            //String destination = dest[idx1];
            String destination = "Paris";
               /* int idx2 = random.nextInt(7);
                if (idx2 == idx1) {
                    idx2 = (idx2 + 1) % dest.length;
                }*/

            //String start = dest[idx2];
            String start = "Lille";
            PathPlannerStrategy pathPlanner = new GooglePathFinder();
            System.out.println(start + ":" + destination);
            Path p = pathPlanner.findPath(new AdressEndPoints(start, destination));

            /*ArrayList<PathPoint> path = new ArrayList<>();
            for(int i=0; i<30;i++){
                path.add(new PathPoint(10+i,20,12));
            }*/
            
            Communicator c = new Communicator("COM"+id,"drone"+id,"localhost:" + MyConstants.KAFKA_ZK_PORT);
            c.run(1,false);
            
            CommandsProvider provider = new CommandsProvider("drone"+id);
            provider.setPath(p.getPathPoints());
            provider.sendCommands();

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