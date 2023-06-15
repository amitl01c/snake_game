package SnakeGame;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener
{
    static final int Screen_width = 800; 
    static final int Screen_height = 800; 
    static final int unit_size = 25;
    static final int game_units = (Screen_width*Screen_height)/unit_size;
    static final int delay = 100 ;
    final int x[] = new int[game_units];
    final int y[] = new int[game_units];
    int bodyPart = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direct='R';
    boolean run= false;
    Timer time;
    Random random;


    
    
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(Screen_width,Screen_height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    
   
    public void startGame(){
        newApple();
        run= true;
        time = new Timer(delay,this);
        time.start();



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
       if(run)
       { 

             for(int i=0;i<Screen_height/unit_size; i++)
                
                 g.setColor(Color.RED);
                 g.fillOval(appleX, appleY, unit_size, unit_size);

             for(int i = 0; i<bodyPart; i++){
                  if(i==0){
                     g.setColor(Color.green);
                     g.fillRect(x[i],y[i], unit_size, unit_size);
                    }
                  else{
                     g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                     g.fillRect(x[i],y[i], unit_size, unit_size);
                    }
                }
                g.setColor(Color.MAGENTA);
                g.setFont(new Font("Ink Free",Font.BOLD,40));
                FontMetrics metrics= getFontMetrics(g.getFont());
                g.drawString("Score: "+applesEaten,( 800 - metrics.stringWidth("Score: "+applesEaten))/2 , g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }

    public void newApple(){
        appleX=random.nextInt((int)(Screen_width/unit_size))*unit_size;
        appleY=random.nextInt((int)(Screen_height/unit_size))*unit_size;

    }

    public void move() {
        for(int i=bodyPart; i > 0; i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direct){
            case 'U':
              y[0]= y[0]- unit_size;
              break;
            
            case 'D':
              y[0]= y[0]+ unit_size;
              break; 
            case 'R':
              x[0]= x[0]+ unit_size;
              break;
            case 'L':
              x[0]= x[0]- unit_size;
              break;    

        }
    }

    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyPart++;
            applesEaten++;
            newApple();
        }
               
    }

    public void checkCollision() {
        //collision with body
        for(int i= bodyPart;i>0; i--){
            if ((x[0]==x[i]) && (y[0]==y[i])){
                run=false;
            }
        }
        //collision with left border
        if (x[0]<0){
            run=false;
        }

        if (x[0]>Screen_width){
            run=false;
        }

        if (y[0]<0){
            run=false;
        }
        
        if (x[0]>Screen_height){
            run=false;
        }

        if (!run){
            time.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.BLUE);
                g.setFont(new Font("Free Style Script",Font.BOLD,40));
                FontMetrics metrics= getFontMetrics(g.getFont());
                g.drawString("Score: "+applesEaten,( 800 - metrics.stringWidth("Score: "+applesEaten))/2 , g.getFont().getSize());
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Free Style Script",Font.BOLD,75));
        FontMetrics metric= getFontMetrics(g.getFont());
        g.drawString("GAME OVER",( 800 - metrics.stringWidth("GAME OVER"))/2 , (Screen_height/2));
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(run)
        {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
     @Override
     public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
            if(direct != 'R'){
                direct='L';
            }
            break;

            case KeyEvent.VK_RIGHT:
            if(direct != 'L'){
                direct='R';
            }
            break;

            case KeyEvent.VK_UP:
            if(direct != 'D'){
                direct='U';
        }
            break;

            case KeyEvent.VK_DOWN:
            if(direct != 'U'){
                direct='D';
            }
            break;
                
                }
        }
 } 
}



        
    
    




