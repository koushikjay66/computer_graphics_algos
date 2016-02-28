/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineclipping;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import static javax.swing.Spring.width;

/**
 *
 * @author Koushik Jay
 */
public class Line implements GLEventListener{

    /**
     * @param args the command line arguments
     */
    
    
    /*
        This stuffy just to configure some thing
        
    */
    static GLProfile profile=GLProfile.get(GLProfile.GL2);
    static GLCapabilities capabilities = new GLCapabilities(profile);
    static GLCanvas glcanvas = new GLCanvas(capabilities);
    
    
    static float x_max=200;
    static float x_min=-200;
    static float y_max=200;
    static float y_min=-200;
    
    static BufferedReader br=null;
    static String coordinates[]=new String[100];
    static int size=0;
   
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Line l=new Line();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(800, 800);
        
         final JFrame frame = new JFrame ("Line Clipping");
       //adding canvas to frame
       frame.getContentPane().add(glcanvas);
       frame.setSize(frame.getContentPane().getPreferredSize());
       frame.setVisible(true);
       //frame.getContentPane().setBackground(Color.white);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       br=new BufferedReader(new FileReader("test/test.txt"));
        String temp;
       while((temp=br.readLine())!=null){
           coordinates[size]=temp;
           size++;
       }
        System.out.println(size);
        
        
       
    }

    @Override
    public void init(GLAutoDrawable glad) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(GLAutoDrawable glad) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        final GL2 gl = glad.getGL().getGL2();
       
          //gl.glMatrixMode(GL_PROJECTION);
        
          gl.glBegin (GL2.GL_POINTS);//static field
          float j=y_min;
          for (float  i = x_min; i <=x_max; i++) {
            gl.glVertex2d(i, y_max);
            gl.glVertex2d(i, y_min);
            gl.glVertex2d(x_min, j);
            gl.glVertex2d(x_max, j);    
            j++;
        }
          // The above Code is for drawing the window
          
          int i=0;
          while(i<size){
          
          
         
        
         String t[]=coordinates[i].split(" ");
         
                float x_0=Float.parseFloat(t[0]);
                float x_1=Float.parseFloat(t[1]);
                float y_0=Float.parseFloat(t[2]);
                float y_1=Float.parseFloat(t[3]);
              /*  
                gl.glColor3d(1, 0, 0);
                while(true){
            gl.glVertex2d(x_0, y_0);
            if(x_0<=x_1){
                x_0++;
            }
            if(y_0<=y_1){
                y_0++;
            }
            else if(x_0>x_1 && y_0>y_1){
                break;
            }      
        }
              */
                /*
            float x_0=0;
            float x_1=500;
            float y_0=0;
            float y_1=0;
        */
        float x_0p=0;
         float y_0p=0;
         float x_1p=0;
         float y_1p=0;
         
         
        //Left Intersection
              
        if(x_0<x_min && (y_0>=y_min && y_0<=y_max)){
            System.out.println("Left Needs to be intersected");
            if((x_1-x_0)==0){
                y_0p=0;
            }else{
           y_0p=y_0+((x_min-x_0)/(x_1-x_0))*(y_1-y_0);
            }
           x_0p=x_min;
        }
             
        
        // Buttom Intersection
        if(y_0<y_min && (x_0>=x_min && x_0<=x_max)){
            System.out.println("Buttom Needs to be intersected");
            if((y_1-y_0)==0){
                x_0p=0;
            }
            else{
            x_0p=x_0+((y_min-y_0)/(y_1-y_0))*(x_1-x_0);
            }
            y_0p=y_min;
        }
             
        
       // Right Intersection
        if(x_1>=x_max && (y_1>=y_min && y_1<=y_max)){
            System.out.println("Right Needs to be intersected");
            if((x_1-x_0)==0){
                y_1p=0;
            }else{
           y_1p=y_0+((x_max-x_0)/(x_1-x_0))*(y_1-y_0);
            }
           
            x_1p=x_max;
           
        }
        
        // Top Intersection
        if(y_1>=y_max && (x_1>=x_min && x_1<=x_max)){
            System.out.println("Top nees to be intersected");
            if((y_1-y_0)==0){
                x_1p=0;
            }
            else{
            x_1p=x_0+((y_max-y_0)/(y_1-y_0))*(x_1-x_0);
            }
            y_1p=y_max;
            System.out.println(x_0p+"k"+y_0p);
        }
          
        
        // Time for 4 Corners
        if(x_0<=x_min && y_0<=y_min){
            x_0p=x_min;
            y_0p=y_min;
            
        }
        if(x_0>=x_max && y_0<=y_min){
            x_0p=x_max;
            y_0p=y_min;
        }
        if(x_1>=x_max && y_1>=y_max){
            x_1p=x_max;
            y_1p=y_max;
        }
        if(x_1<x_min && y_1>y_max){
            x_1p=x_min;
            y_1p=y_max;
        }
               
        else{
            System.out.println("The Line was Completely Rejected");
        }
       gl.glColor3d(0.5, 1, 0);
        while(true){
            gl.glVertex2d(x_0p, y_0p);
            if(x_0p<=x_1p){
                x_0p++;
            }
            if(y_0p<=y_1p){
                y_0p++;
            }
            else if(x_0p>x_1p && y_0p>y_1p){
                break;
            }      
        }
        i++;
       }
       
          gl.glEnd();
          
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         GL gli = glad.getGL();
         
       ((GL2ES1) gli).glOrtho (-800, i2, -800, i3, -1.0, 1.0);
        
    }
    
}

