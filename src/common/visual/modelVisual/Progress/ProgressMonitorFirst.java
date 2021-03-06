package common.visual.modelVisual.Progress;

import common.Adapter;
import common.Aggregator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Cats on 01.11.2015.
 */
public class ProgressMonitorFirst extends JPanel
        implements ActionListener,
        PropertyChangeListener
   {
        private ProgressMonitor progressMonitor;
        private JButton startButton;
        private JTextArea taskOutput;
        private Task task;
        private Adapter adapter;
        public ProgressMonitorFirst() {
           super(new BorderLayout());

           //Create the demo's UI.
           startButton = new JButton("Start");
           startButton.setActionCommand("start");
           startButton.addActionListener(this);

           taskOutput = new JTextArea(5, 20);
           taskOutput.setMargin(new Insets(5,5,5,5));
           taskOutput.setEditable(false);

           add(startButton, BorderLayout.PAGE_START);
           add(new JScrollPane(taskOutput), BorderLayout.CENTER);
           setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

       }
        public ProgressMonitorFirst(Adapter adapter) {
           super(new BorderLayout());
           this.adapter=adapter;

           //Create the demo's UI.
           startButton = new JButton("Start");
           startButton.setActionCommand("start");
           startButton.addActionListener(this);

           taskOutput = new JTextArea(5, 20);
           taskOutput.setMargin(new Insets(5,5,5,5));
           taskOutput.setEditable(false);

           add(startButton, BorderLayout.PAGE_START);
           add(new JScrollPane(taskOutput), BorderLayout.CENTER);
           setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

       }
        class Task extends SwingWorker<Void, Void> {
            @Override
            public Void doInBackground() {
                //Random random = new Random();
                int progress = 10;
                setProgress(progress);
                //setProgress(progress+1);
                try {
                    while (progress < 100 && !isCancelled()) {
                        //Sleep for up to one second.
                        //Thread.sleep(random.nextInt(1000));
                        //Make random progress.
                        //progress += random.nextInt(10);
                        progress = adapter.getPercentCompleted();
                        //System.out.println(progress);
                        setProgress(Math.min(progress, 100));
                        Thread.sleep(1000);
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) {
                    System.out.println(ignore.getLocalizedMessage()+"--------------------------");
                }
                return null;
            }

            @Override
            public void done() {
                Toolkit.getDefaultToolkit().beep();
                //startButton.setEnabled(true);
                //progressMonitor.setProgress(0);
            }
        }
        /**
         * Invoked when the user presses the start button.
         */
        public void actionPerformed(ActionEvent evt) {
            progressMonitor = new ProgressMonitor(ProgressMonitorFirst.this,
                    "Running a Long Task",
                    "", 0, 100);
            progressMonitor.setProgress(0);
            task = new Task();
            task.addPropertyChangeListener(this);
            task.execute();
            //progressMonitor.setProgress(1);
            startButton.setEnabled(false);
            String message =
                    String.format("Start search! \n");
            //progressMonitor.setNote(message);
            taskOutput.append(message);

        }
        /**
         * Invoked when task's progress property changes.
         */
        public void propertyChange(PropertyChangeEvent evt) {
            if (Objects.equals("progress", evt.getPropertyName())) {
                int progress = (Integer) evt.getNewValue();
                progressMonitor.setProgress(progress);
                String message =
                        String.format("Completed %d%%.\n", progress);
                progressMonitor.setNote(message);
                taskOutput.append(message);
                if (progressMonitor.isCanceled() || task.isDone()) {
                    Toolkit.getDefaultToolkit().beep();
                    if (progressMonitor.isCanceled()) {
                        task.cancel(true);
                        taskOutput.append("Task canceled.\n");
                    } else {
                        taskOutput.append("Task completed.\n");
                    }
                    //startButton.setEnabled(true);
                }
            }

        }
        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event-dispatching thread.
         */
        public void createAndShowGUI() {
            //Create and set up the window.
            JFrame frame = new JFrame("ProgressMonitorDemo");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Create and set up the content pane.
            //JComponent newContentPane = new ProgressMonitorFirst();
            JComponent newContentPane = this;
            newContentPane.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane);

            //Display the window.
            frame.pack();
            frame.setVisible(true);
            //actionPerformed(null);
            /*progressMonitor = new ProgressMonitor(ProgressMonitorFirst.this,
                    "Running a Long Task",
                    "", 0, 100);
            progressMonitor.setProgress(0);
            task = new Task();
            task.addPropertyChangeListener(this);
            task.execute();
            startButton.setEnabled(false);*/

        }
}
