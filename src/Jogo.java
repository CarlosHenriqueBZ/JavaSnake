import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class Jogo extends Canvas implements Runnable, KeyListener {

    public Node[] nodeSnake = new Node[20];


    public boolean left, right,up,down;
    public int score = 0;
    public int macaX = 0, macaY = 0;




    public Jogo(){
        this.setPreferredSize(new Dimension(480, 480));
        for (int i = 0; i < nodeSnake.length; i++){
            nodeSnake[i] = new Node(0,0);

        }
        this.addKeyListener(this);
    }


     public void tick() {

         for (int i = nodeSnake.length - 1; i > 0; i--) {

             nodeSnake[i].x = nodeSnake[i - 1].x;
             nodeSnake[i].y = nodeSnake[i - 1].y;

         }


        if (right){
            nodeSnake[0].x++;
        }else if (up){
            nodeSnake[0].y--;
        }else if (down){
            nodeSnake[0].y++;
        }else if (left){
            nodeSnake[0].x--;
        }

        if(new Rectangle(nodeSnake[0].x,nodeSnake[0].y,10,10).intersects(new Rectangle(macaY,macaX,5,5)));
         {
             macaX = new Random().nextInt(480-10);
             macaY = new Random().nextInt(480-10);
             score++;
             System.out.println("Pontos: " + score);
         }
     }

     public void render(){
         BufferStrategy bs = this.getBufferStrategy();
         if (bs == null){
             this.createBufferStrategy(3);
             return;
         }
         Graphics g = bs.getDrawGraphics();
         g.setColor(Color.black);
         g.fillRect(0, 0, 480, 480);
         for(int i = 0; i < nodeSnake.length; i++) {
             g.setColor(Color.GREEN);
             g.fillRect(nodeSnake[i].x, nodeSnake[i].y, 10, 10);
         }

             g.setColor(Color.red);
             g.fillRect(macaX, macaY,5, 5);

             g.dispose();
             bs.show();
     }

    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        JFrame frame = new JFrame("Snake");
        frame.add(jogo);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        new Thread(jogo).start();
    }

    @Override
    public void run() {

        while (true){
            tick();
            render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            right = true;
            down = false;
            left = false;
            up = false;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left = true;
            right = false;
            down = false;
            up = false;
        }else if (e.getKeyCode() == KeyEvent.VK_UP){
            up = true;
            right = false;
            left = false;
            down = false;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            down = true;
            right = false;
            left = false;
            up = false;
    }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
