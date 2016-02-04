/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3_usingmidpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    
    // The below Code is for Reading From FIle
    
    static BufferedReader br=null;
    static String coordinates[]=new String[100];
    static int size=0;
   
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Line l=new Line();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(600, 400);
        
         final JFrame frame = new JFrame ("straight Line");
       //adding canvas to frame
       frame.getContentPane().add(glcanvas);
       frame.setSize(frame.getContentPane().getPreferredSize());
       frame.setVisible(true);
       //frame.getContentPane().setBackground(Color.white);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
       br=new BufferedReader(new FileReader("test/sample.txt"));
       String temp;
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
        
        final GL2 gl = glad.getGL().getGL2();
          gl.glBegin (GL2.GL_POINTS);//static field
         
            int i=0;
            while(i<size){
                String t[]=coordinates[i].split(" ");
                float x_0=Float.parseFloat(t[0]);
                float y_0=Float.parseFloat(t[1]);
                float x_1=Float.parseFloat(t[2]);
                float y_1=Float.parseFloat(t[3]);

                float dy=y_1-y_0;
                float dx=x_1-x_0;
                float m=Math.abs(dy)/Math.abs(dx);
          
                 if(Math.abs(dy)>=Math.abs(dx)){
                    //Which means m>=|1|
                    // For zone 1,2,5, 6
                    if(dx>=0 && dy>=0){
                        float d=dy-2*dx;
                        float dn=-2*dx;
                        float dne=-2*dy-2*dx;
                        // Zone 1
                        while(y_0<y_1){
                            if(d>=0){
                                y_0=y_0+0.01f;
                                d=d+dn;
                            }else{
                                x_0=x_0+0.01f;
                                y_0=y_0+0.01f;
                                d=d+dne;
                            }
                            //System.out.println(x_0+" "+y_0);
                            gl.glColor3d(0.5, 1.0, 0.5);
                            // Light Green For Zone 1
                            gl.glVertex2d(x_0, y_0);
                        }
                        System.out.println("Input Line "+(i+1)+" is on Zone 1, Color is Light Green");
                    }
                    else if(dx<0 && dy>=0){
                        // Zone 2
                        float d=-dy-2*dx;
                        float dn=-2*(dy+dx);
                        float dnw=-2*dy+2*dx;
                        
                         while(y_0<y_1){
                            if(d>=0){
                                y_0=y_0+0.01f;
                                d=d+dn;
                            }else{
                                x_0=x_0-0.01f;
                                y_0=y_0+0.01f;
                                d=d+dnw;
                            }
                            //System.out.println(x_0+" "+y_0);
                            gl.glColor3d(1, 0.5, 0);
                            //Orange
                            gl.glVertex2d(x_0, y_0);
                        }
                         System.out.println("Input Line "+(i+1)+" is on Zone 2, Color is Orange");
                    }
                    else if(dx<0 && dy<0){
                        // Zone 5
                        float d=-dy+2*dx;
                        float ds=2*dx;
                        float dsw=2*dx-2*dy;
                         while(y_0>=y_1){
                             if(d<0){
                                y_0=y_0-0.01f;
                                x_0=x_0-0.01f;
                                d=d+dsw;
                            }else{
                                x_0=x_0-0.01f;
                                
                                d=d+ds;
                            }
                            //System.out.println(x_0+" "+y_0);
                            gl.glColor3d(1, 1, 1);
                            gl.glVertex2d(x_0, y_0);
                        }
                         System.out.println("Input Line "+(i+1)+" is on Zone 5, white");
                    }
                    else if(dx>0 && dy<0){
                        // Zone 6
                        float d=dy+2*dx;
                        float dse=2*(dy+dx);
                        float ds=-2*dx;
                         while(y_0>y_1){
                             if(d>0){
                                 //dse
                                 y_0=y_0-0.01f;
                                 x_0=x_0+0.01f;
                                 d=d+dse;
                             }else{
                                 
                                y_0=y_0-0.01f;
                                d=d+ds;
                             }
                            
                            //System.out.println(x_0+" "+y_0);
                            gl.glColor3d(0.5, 1, 0);
                            //Greenish Yellow
                            gl.glVertex2d(x_0, y_0);
                        }
                         System.out.println("Input Line "+(i+1)+" is on Zone 6, Color is Greenish Yellow");
                    }
                    
                    
                }else if(Math.abs(dy)<Math.abs(dx)){
                        // For zone 0, 3, 4, 7
                        //Which means m<1
                    
                    if(dx>0 && dy>0){
                        // Zone 0
                        float d=2*dy-dx;
                        float de=2*dy;
                        float dne=2*dy-2*dx;
                         while(x_0<x_1){
                             if(d>0){
                                 //dne will be selected
                                 y_0=y_0+0.01f;
                                 x_0=x_0+0.01f;
                                 d=d+dne;
                             }else{
                                 x_0=x_0+0.01f;
                                 d=d+de;
                             }
                            
                          //  System.out.println(x_0+" "+y_0);
                            gl.glColor3d(1, 1, 0);
                            //Yellow
                            gl.glVertex2d(x_0, y_0);
                        }
                         System.out.println("Input Line "+(i+1)+" is on Zone 0, Yellow");
                    }
                    else if(dx<0 && dy>0){
                        // Zone 3
                        float d=-2*dy-dx;
                        float dw=-2*dy;
                        float dnw=-2*dy-2*dx;
                         while(x_0>x_1){
                            if(d>0){
                                //dw
                                x_0=x_0-0.01f;
                                d=d+dw;
                            }else{
                                y_0=y_0+0.01f;
                                x_0=x_0-0.01f;
                            }
                            //System.out.println(x_0+" "+y_0);
                            gl.glColor3d(1, 0, 0);
                            //Red
                            gl.glVertex2d(x_0, y_0);
                        }
                         System.out.println("Input Line "+(i+1)+" is on Zone 3, Color Red");
                    }
                    else if(dx<0 && dy<0){
                        // Zone 4
                        float d=-2*dy+dx;
                        float dw=-2*dy;
                        float dsw=-2*dy+2*dx;
                         while(x_0>x_1){
                             
                             if(d>0){
                                 //dsw
                                 y_0=y_0-0.001f;
                                 x_0=x_0-0.001f;
                                 d=d+dsw;
                             }else{
                                 x_0=x_0-0.001f;
                                 d=d+dw;
                             }
                            
                            //System.out.println(x_0+" "+y_0);
                            gl.glColor3d(0, 0, 1);
                            //Blue
                            gl.glVertex2d(x_0, y_0);
                        }
                         System.out.println("Input Line "+(i+1)+" is on Zone 4, Color Blue");
                    }
                    else if(dx>0 && dy<0){
                        // Zone 7
                        float d=2*dy+dx;
                        float de=2*dy;
                        float dse=2*dy+2*dx;
                         while(x_0<x_1){
                             if(d>0){
                                 //de
                                 x_0=x_0+0.001f;
                                 d=d+de;
                             }else{
                                 y_0=y_0-0.001f;
                                 x_0=x_0+0.001f;
                                 d=d+dse;
                             }
                            //System.out.println(x_0+" "+y_0);
                            gl.glColor3d(1, 1, 1);
                            //White
                            gl.glVertex2d(x_0, y_0);
                        }
                         System.out.println("Input Line "+(i+1)+" is on Zone 7, color White");
                    }
                }
                i++;    
            }
            System.out.println(i-1);
          gl.glEnd();
          
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}


