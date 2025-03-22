package AISim2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * Example 2D String visualization
 * @author ICS4UE
 * @version Nov 2023
 */
public class Board extends JFrame{
	
	
	
    final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();
    final int GRIDSIZE = MAX_Y/20;

    final int CENTER_COL = (MAX_X/4/GRIDSIZE)/2;
    final int CENTER_ROW = (MAX_Y/2/GRIDSIZE)/2;
    
    
    //feel free to modify the board size to fit your computer screen
    //remember to change the values in the creature file as well
    public final int MAX_XS = 425;
    public final int MAX_YS = 215;
    
    private int[][] boardhealth = new int[MAX_XS][MAX_YS];
    private Container container;
    
    private JFrame panell;
    
    //pixel size
    public static final int cscale=3;
    //board health coefficient, base amount of health that land has
    public static final int boardhealthcoeff=50;
    //amount the land loses energy
    public static final int landdec=25;
    //amount the land gains energy
    public static final int landinc=2;
    
    public static final int density=10;
    
    //amount of energy gained from eating
    public static final int egc=100;//28
  //amount of stat gained from eating
    public static final int sgc=150;
    //base stat total maximum for reproducing
    //public static final int bstmax=750;
    
    
    
    
    
    private GraphicsPanel panel;
    private MyMouseListener mouseListener;
    
    private int creaturecount=0;
    
    private Creature[][] creatures=new Creature[MAX_XS][MAX_YS];
    private ArrayList<Integer[]> pointers=new ArrayList<Integer[]>();
    
    
    public void initialize () {
    	
    	
        this.panel = new GraphicsPanel();
        this.panell = new JFrame();
        this.panel.setBackground(Color.BLACK);
        container=this.getContentPane();
        this.getContentPane().add(BorderLayout.CENTER, panel);
        mouseListener=new MyMouseListener();
        container.addMouseListener(mouseListener);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_X/4, MAX_Y/2);
        
        
        this.setVisible(true);
        creaturecount=0;
        for(int n=0;n<MAX_XS*MAX_YS/(density*2);n++) {//
        	int tempx=(int)(Math.random()*MAX_XS);
        	int tempy=(int)(Math.random()*MAX_YS);
        	if(creatures[tempx][tempy]==null) {
	        	creatures[tempx][tempy]=new Creature(
	        			(Math.random()),
	        			(int)(Math.random()*24)+1,
	        			(int)(Math.random()*200)+25,
	        			10000,
	        			tempx,
	        			tempy,
	        			new double[]{
	        					Math.random()*100+10,
	        					Math.random()*100+1,
	        					Math.random()*100+1,
	        					Math.random()*100+1,
	        					Math.random()*100+1
	        					}
	        			);
	        	pointers.add(new Integer[] {tempx,tempy});
	        	
        	}
        	
        }
        for (int x=0;x<MAX_XS;x++) {
        	for (int y=0;y<MAX_YS;y++) {
            	boardhealth[x][y]=boardhealthcoeff;
            	
            	//UNCOMMENT THE FOLLOWING FOR PETRI DISH MODE
            	
            	//if((x%85>=0&&x%85<=10)) {//|| (y%215>=0&&y%215<=10)
            	//	boardhealth[x][y]=-12000;
        		//}
            }
        	
        }
        
    }
    
    
    public int getSizeAt(int x,int y){
    	if (creatures[(x+MAX_XS)%MAX_XS][(y+MAX_YS)%MAX_YS]==null) {
    		return 0;
    	}
    	return creatures[(x+MAX_XS)%MAX_XS][(y+MAX_YS)%MAX_YS].getSize();
    	
    } 
    
    public void createNewCreature(double groundeat, int size, int reprolimit, int baseenergy,int xpos,int ypos,double[] valchart){
    	for(int n=0;n<4;n++) {
    		int newx=(xpos+(int)(Math.random()*11)-5+MAX_XS)%MAX_XS;
    		int newy=(ypos+(int)(Math.random()*11)-5+MAX_YS)%MAX_YS;
    		if(creatures[newx][newy]==null){
    			creatures[newx][newy]=new Creature(groundeat,size,reprolimit,baseenergy,newx,newy,valchart);
    			creaturecount+=1;
    			pointers.add(new Integer[] {newx,newy});
    			return;
    		}
    	}
    }
    
    private class GraphicsPanel extends JPanel {
      
        @Override
        public void paintComponent(Graphics g) {
          
          
            super.paintComponent(g);
            for (int x=0;x<MAX_XS;x++) {
            	for (int y=0;y<MAX_YS;y++) {
                	int s=boardhealth[x][y]/2;
                	if(s>100) {
                		s=100;
                	}
                	if(s<0) {
                		s=0;
                	}
                	g.setColor(new Color(s,s,s));
                	g.fillRect(x*cscale,y*cscale,cscale,cscale);
                }
            	
            }
            for(int getter=0;getter<pointers.size();getter++) {
            	
            	Integer[] n=pointers.get(getter);
            	int x=n[0];
            	int y=n[1];
        		if(creatures[x][y]!=null && creatures[x][y].isAlive()) {
            	
            		int r=(int)(creatures[x][y].getGroundEat()*150)+50;
            		if(r>255) {
            			r=255;
            		}
            		if(r<0) {
            			r=0;
            		}
            		int gr=creatures[x][y].getSize()*5+50;
            		if(gr>255) {
            			gr=255;
            		}
            		if(gr<0) {
            			gr=0;
            		}
            		int b=creatures[x][y].getReproLimit()+50;
            		if(b>255) {
            			b=255;
            		}
            		if(b<0) {
            			b=0;
            		}
            		//try {
            			g.setColor(new Color(r,gr,b));
            		//}catch(Exception e) {}
            			
            			g.fillOval(creatures[x][y].getX()*cscale,creatures[x][y].getY()*cscale,cscale,cscale);
            				
            			//experimental: 
            			//this makes larger creatures drawn larger
            			//comment out the other thing though
            			//int sizetest=creatures[x][y].getSize();
            			//g.fillOval(creatures[x][y].getX()*cscale-sizetest/20,creatures[x][y].getY()*cscale-sizetest/20,cscale*sizetest/10+1,cscale*sizetest/10+1);
	            	
	            	
	            	
            			int movedir=creatures[x][y].move(
	            			boardhealth[(creatures[x][y].getX()+MAX_XS-1)%MAX_XS][(creatures[x][y].getY()+MAX_YS)%MAX_YS], 
	            			boardhealth[(creatures[x][y].getX()+MAX_XS+1)%MAX_XS][(creatures[x][y].getY()+MAX_YS)%MAX_YS], 
	            			boardhealth[(creatures[x][y].getX()+MAX_XS)%MAX_XS][(creatures[x][y].getY()+MAX_YS-1)%MAX_YS], 
	            			boardhealth[(creatures[x][y].getX()+MAX_XS)%MAX_XS][(creatures[x][y].getY()+MAX_YS+1)%MAX_YS], 
	            			getSizeAt((creatures[x][y].getX()+MAX_XS-1)%MAX_XS,creatures[x][y].getY()), 
	            			getSizeAt((creatures[x][y].getX()+2)%MAX_XS-1,creatures[x][y].getY()), 
	            			getSizeAt(creatures[x][y].getX(),(creatures[x][y].getY()+MAX_YS-1)%MAX_YS), 
	            			getSizeAt(creatures[x][y].getX(),(creatures[x][y].getY()+1)%MAX_YS)	            			);
	            	creatures[x][y].moveChar(movedir);
	            	
	            	
	            	
	            	//if(boardhealth[creatures[x][y].getX()][creatures[x][y].getY()]<-9000) {
	            	//	creatures[x][y].kill();
	            	//}
	            	creatures[x][y].changeStats((int)(boardhealth[creatures[x][y].getX()][creatures[x][y].getY()]*(creatures[x][y].getGroundEat()+0.05)));
	            	if(creatures[x][y].getGroundEat()>0) {
	            		boardhealth[creatures[x][y].getX()][creatures[x][y].getY()]-=landdec;
	            	}else {
	            		boardhealth[creatures[x][y].getX()][creatures[x][y].getY()]/=0-creatures[x][y].getGroundEat();
	            	}
	            	try {
		            	int a=creatures[x][y].getX();
		            	int c=creatures[x][y].getY();
	            		if(creatures[a][c]!=null && creatures[x][y].isAlive()) {
	            			if(creatures[a][c].getX()==creatures[x][y].getX() && creatures[a][c].getY()==creatures[x][y].getY()) {
	            				if(creatures[a][c].getSize()>creatures[x][y].getSize()) {// && creatures[a][c].getSize()<creatures[x][y].getSize()+20
	            					creatures[a][c].gainEnergy(creatures[x][y].getSize()*egc);
	            					creatures[a][c].gainStat(creatures[x][y].getSize()*sgc);
	            					creatures[x][y].kill();
	            				}
	            				else if(creatures[a][c].getSize()<=creatures[x][y].getSize()){// && creatures[a][c].getSize()+20>creatures[x][y].getSize()
	            					creatures[x][y].gainEnergy(creatures[a][c].getSize()*egc);
	            					creatures[x][y].gainStat(creatures[a][c].getSize()*sgc);
	            					creatures[a][c].kill();
	            					
	            					
	            					creatures[a][c]=creatures[x][y];
	            					//pointers.get(getter)[0]=a;
	            					//pointers.get(getter)[0]=c;
	            				}
	            				
	            				//this part of the code was used to make a special case
	            				//where very large creatures won't eat very small creatures
	            				/**
	            				else if(creatures[a][c].getSize()<creatures[x][y].getSize()-20) {
	            					creatures[a][c].gainEnergy(creatures[x][y].getSize()/5);
	            					//creatures[x][y].changeHealth(-1);
	            				}
	            				else if(creatures[x][y].getSize()<creatures[a][c].getSize()-20) {
	            					creatures[x][y].gainEnergy(creatures[a][c].getSize()/5);
	            					//creatures[a][c].changeHealth(-1);
	            					//if(creatures[a][c].getHealth()<=0) {
	            					//	creatures.remove(y);
	            					//}
	            				}
	            				*/
	            				
	            			}
	            		}
            		}catch(Exception e){}
            		if (creatures[x][y]==null) {
            			//this is just here to filter out the case where the thing is null
            			creaturecount+=0;
            			
            		}
            		else if(creatures[x][y].isAlive()==false) {
						creatures[x][y]=null;
						creaturecount-=1;
						pointers.remove(getter);
						getter-=1;
					}
					else if(creatures[x][y].getEnergy()<0) {
						
						creatures[x][y]=null;
						creaturecount-=1;
						pointers.remove(getter);
						getter-=1;
	            	}
					else if((int)(Math.random()*creatures[x][y].randDeathChance()*1000/creaturecount)==0){
						
						creatures[x][y]=null;
						creaturecount-=1;
						pointers.remove(getter);
						getter-=1;
					}
					else if(creatures[x][y].getStat()>=creatures[x][y].getReproLimit()*2+100) {
						//if(creaturecount<MAX_XS*MAX_YS/density) {// &&creatures[x][y].getBST()<bstmax
							createNewCreature(creatures[x][y].getGroundEat(), creatures[x][y].getSize(), creatures[x][y].getReproLimit(), creatures[x][y].getEnergy(),creatures[x][y].getX(),creatures[x][y].getY(),creatures[x][y].valchart());
							creatures[x][y].resetStat();
							
						//}							
					}
	   
        		}else if (creatures[x][y].isAlive()==false) {
        			creatures[x][y]=null;
					creaturecount-=1;
					pointers.remove(getter);
					getter-=1;
                }
            	
            
            }
            
            //
            for (int x=0;x<MAX_XS;x++) {
            	for (int y=0;y<MAX_YS;y++) {
                	boardhealth[x][y]+=landinc;
                	
                	//these two lines allow you to give more energy to certain areas
                	//boardhealth[x][y]+=1+x/85;
                	//boardhealth[x][y]+=1+y/43;
                	
                }
            }
            
            
            
            this.repaint();
            
            //this makes the program run slower, better for small sizes
            //try  {Thread.sleep(200);} catch(Exception e){}
        }
        
        
    }
    
    class MyMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent e){
          
        }
        public void mousePressed(MouseEvent e){   // MUST be implemented even if not used!
        }
        public void mouseReleased(MouseEvent e){  // MUST be implemented even if not used!
          
        
        }
        public void mouseEntered(MouseEvent e){   // MUST be implemented even if not used!
        }
        public void mouseExited(MouseEvent e){    // MUST be implemented even if not used!
        }
    }
    
}
    

//green means large size
//red means higher ground eating
//blue means high reprolimit
