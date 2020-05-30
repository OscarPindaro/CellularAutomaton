package controller.action;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Action are added. To execute them yhr method runActions should be called
 */
public class ActionExecutor implements ActionExecutorInterface {

    private static final Logger logger = Logger.getLogger(ActionExecutor.class.getName());

    private final ExecutorService executor= Executors.newFixedThreadPool(4);

    private final BlockingQueue<ActionInterface> queue = new LinkedBlockingQueue<>();

    public void addAction(ActionInterface a){
        try{
            queue.put(a);
        }
        catch(InterruptedException ie){
            logger.log(Level.SEVERE, "Problems while adding an action in the queue. This action may be aborted now");
        }
    }

    /**
     * run actions till the queue is empty. Does not run forever!!!!
     */
    public void runActions(){
        while(!queue.isEmpty()){
            executor.execute(() -> {
                ActionInterface a = null;
                try{
                    a = queue.take();
                }catch (InterruptedException ie){
                    logger.log(Level.SEVERE, "Problems while taking an action in the queue. This action may be aborted now");
                }
                a.perform();
            });
        }
    }

}
