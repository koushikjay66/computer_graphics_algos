/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohen.sutherland;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

/**
 *
 * @author 13101206
 */
public class Line implements GLEventListener{

    /**
     * @param args the command line arguments
     */
    
    static GLProfile profile = GLProfile.get(GLProfile.GL2);
    static GLCapabilities capabilities = new GLCapabilities(profile);
    // The canvas 
    static GLCanvas glcanvas = new GLCanvas(capabilities);
    
    // Now File Reading Operation
    
    static BufferedReader br;
    static String coordinates[]=new String[100];
    static int size=0;
    static int x_min=-1;
    static int y_min=-1;
    static int x_max=1;
    static int y_max=1;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        
          
	      Line l = new Line();
	      //creating frame
	      glcanvas.addGLEventListener(l);
	      glcanvas.setSize(600, 400);
	      
	      final JFrame frame = new JFrame ("straight Line");
	      //adding canvas to frame
	      frame.getContentPane().add(glcanvas);
	      frame.setSize(frame.getContentPane().getPreferredSize());
	      frame.setVisible(true);
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
              br=new BufferedReader(new FileReader("test/sample.txt"));
              String temp="";
              
              while((temp=br.readLine())!=null){
                  coordinates[size]=temp;
                  size++;
              }
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
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
       
    }
    
    public int makeCode(float x, float y){
        int outCode=0;
        if(x<x_min){
            outCode+=1;
        }else if(x>x_max){
            outCode+=2;
        }
        if(y<y_min){
            outCode+=4;
        }else if(y>y_max){
            outCode+=8;
        }
        
        return outCode;
    }// Done with OutCode
    
       public boolean cohen_sutherland(GL2 gl, float x_0, float y_0, float x_1, float y_1){
           
           int outCode, outCode_0, outCode_1;
           int Top=8, Buttom=4, Left=1, Right=2;
           float x, y;
           outCode_0=makeCode(x_0, y_0);
           outCode_1=makeCode(x_1, y_1);
           
           while(true){
               if((outCode_0 | outCode_1)==0){
                   // Completely Accepted
                   
                   gl.glVertex2d(x_0, y_0);
                   gl.glVertex2d(x_1, y_1);
                   
                   return true;
               }else if((outCode_0&outCode_1)>0){
                   // Completely Rejected
                   return false;
               }else{
                   if(outCode_0==1){
                       outCode=outCode_0;
                   }else{
                       outCode=outCode_1;
                   }
                   
                   // Now for Top Clip Line
                   if((outCode & Top) >=1){
                       //This is meaning same value
                       // Or As Same as OUT
                       x=x_0+((y_max-y_0)*(x_1-x_0))/(y_min-y_0);
                       y=y_max;
                   }else if((outCode & Buttom)>=1){
                       x=x_0+((y_min-y_0)*(x_1-x_0))/(y_1-y_0);
                       y=y_min;
                   }else if((outCode & Left)>=1){
                       y=y_0+((x_min-x_0)*(x_1-x_0))/(y_1-y_0);
                       x=x_min;
                   }else if((outCode & Right)>=1){
                       y=y_0+((x_max-x_0)*(x_1-x_0))/(y_1-y_0);
                       x=x_max;
                   }
               }// End of Else
           }// End of While Loop
       }//End of Method
    
    
    
}//End of Class
