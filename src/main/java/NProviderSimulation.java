import endPoints.AdressEndPoints;
import path.Path;
import path.PathPoint;
import pathFinder.GooglePathFinder;
import pathFinder.PathPlannerStrategy;
import pathToNavCommands.CommandsProvider;
import utils.MyConstants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sy306571 on 30/01/16.
 */
public class NProviderSimulation {
    public static void main(String args[]) {
        int n = MyConstants.NUMBER_OF_DRONES;
        for (int i = 0; i < n; i++) {
            Thread t = new Thread(new NProviderSimulation.ProviderTask(i));
            t.start();
        }
    }
    static class ProviderTask implements Runnable {

        int id;

        public ProviderTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
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

            ArrayList<PathPoint> path = new ArrayList<>();
            for(int i=0; i<30;i++){
                path.add(new PathPoint(10+i,20,12));
            }

            CommandsProvider provider = new CommandsProvider("drone"+id);
            provider.setPath(path);
            provider.sendCommands();
        }

    }
}
