package baduk;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Baduk implements MouseListener, MouseMotionListener {

    JFrame j = new JFrame();
    JPanel p = new JPanel();
    JPanel pp = new JPanel();
    Graphics g = null;

    String turn = "white";
    
    int x = 0, y = 0;

    String[][] board = new String[19][19];
    
    public Baduk() {

        setGUI();
        
        initBoard();
        
        paintBoard();

        j.addMouseListener(this);
        
        j.addMouseMotionListener(this);
    }
    
    void initBoard() {
        for(int i=0; i<19; i++) {
            for(int j=0; j<19; j++) {
                board[i][j] = "";
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = (int)((double)e.getX()/(double)50);
        y = (int)((double)e.getY()/(double)50);
        if(turn.equals("white")) {
            if(!(x == 20 || y == 20)) {
                g.setColor(Color.WHITE);
                g.fillOval(x*50, y*50-25-25, 50, 50);
            }
        }
        if(turn.equals("black")) {
            if(!(x == 20 || y == 20)) {
                g.setColor(Color.BLACK);
                g.drawOval(x*50, y*50-25-25, 50, 50);
            }
        }
        paintBoard();
    }

    void setGUI() {
        
        j.setTitle("Baduk (Go)");
        j.setLayout(null);
        j.setBounds(0,0,1200,1100);
        p.setBounds(0,0,1000,1000);
        j.add(p);
        pp.setBounds(1000,0,200,900);
       
        JButton b = new JButton("New Start. - 새로 시작");
        b.setBounds(40, 10, 163, 20);
        pp.add(b);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                turn = "white";

                initBoard();

                paintBoard();
            }
        });
        
        j.add(pp);
        j.add(p);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setVisible(true);
        setGraphics();
    }
    
    void setGraphics() {
        g = p.getGraphics();
    }
    
    void paintBoard() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                g.setColor(new Color(200,150,55));
                g.fillRect(0,0,1000,1000);
                g.setColor(Color.WHITE);
                for(int i=0; i<19; i++) {
                    g.drawLine(i*50+25, 0+25, i*50+25, 900-25);
                }
                for(int i=0; i<19; i++) {
                    g.drawLine(25, i*50-25, 925, i*50-25);
                }
                for(int i=0; i<19; i++) {
                    for(int j=0; j<19; j++) {
                        if(board[i][j].equals("white")) {
                            g.setColor(Color.WHITE);
                            g.fillOval(j*50, i*50-25-25, 50, 50);
                        }
                        else if(board[i][j].equals("black")) {
                            g.setColor(Color.BLACK);
                            g.fillOval(j*50, i*50-25-25, 50, 50);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        return;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
            int x = (int)((double)e.getX()/(double)50);
            int y = (int)((double)e.getY()/(double)50);
            board[y][x] = "";
        } else {
            if(turn.equals("white")) {
                int x = (int)((double)e.getX()/(double)50);
                int y = (int)((double)e.getY()/(double)50);
                if(x < 20 && y < 20) {
                    if(board[y][x].equals("")) {
                        g.setColor(Color.WHITE);
                        g.fillOval(x*50-25, y*50-25, 50, 50);
                        board[y][x] = turn;
                        turn = "black";
                    }
                }
            }
            else if(turn.equals("black")) {
                int x = (int)((double)e.getX()/(double)50);
                int y = (int)((double)e.getY()/(double)50);
                if(x < 20 && y < 20) {
                    if(board[y][x].equals("")) {
                        g.setColor(Color.BLACK);
                        g.fillOval(x*50-25, y*50-25, 50, 50);
                        board[y][x] = turn;
                        turn = "white";
                    }
                }
            }
        }
        paintBoard();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        return;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        return;
    }
    
    public static void main(String[] args) {
    
        new Baduk();
    }
}