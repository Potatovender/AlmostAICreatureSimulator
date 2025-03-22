package AISim2;

import java.util.ArrayList;

public class Creature{
	//mutation coefficient
	private static double muteCoeff=1.2;
	
	//distance offspring can spawn from parent
	private static int locVar=5;
	
	//health coefficient from eating other players (multiplier)
	private static double hcmc=1;
	//health coefficient from eating other players (adder)
	private static int hcac=25;
	//base stat increase from eating other players
	private static double bcc=20.0;
	
	//base stat increase regular
	private static double bsic=1.5;
	//base energy loss multiplier
	private static double bec=0.05;
	
	private static int hm=2;
	private static int gm=3;
	private static int rm=1;
	private static int sm=9;
	public static final int bstmax=2000000000;
	
	
  private int xpos;
  private int ypos;
  private boolean alive=true;
  private int size=10;
  //increase size=increase energy loss
  private int reproLimit=100;
  //decrease reproLimit=increase death chance
  private double groundeat=0.05;
  //increase groundeat = smaller fraction of ground food that gets eaten
  private double stat=0;
  //controls reproduction, energy
  private int energy=10000;
  
  //if energy reaches 0, they die
  
  //feel free to modify the board size to fit your computer screen
  //remember to change the values in the board file as well
  private final int MAX_X = 425;
  private final int MAX_Y = 215;
  
  //randomly die from random number (1 to reprolimit)
  
  private double[] valchart=new double[]{100,1,1,1,1};
  
  //evolution is based on how much energy you have upon reproducing: less energy = more variation 10-energy/1000

  Creature(double groundeat,int size,int reproLimit,int energy, int xpos, int ypos,double[] valchart){
    //fractional increasing for evo:math.pow(muteCoeff,((int)(Math.random()*(evo*2+1))-evo))
    int evo=10-energy/1000;
    if(evo<0) {
    	evo=0;
    }
    
    for (int x=0;x<5;x++) {
    	this.valchart[x]=valchart[x]*Math.pow(muteCoeff,((int)(Math.random()*(evo*10+1))-evo*5))+Math.random()*2-1;
    	
    }
    
    
    this.size=(int)(size*Math.pow(muteCoeff,((int)(Math.random()*(evo*2+1))-evo)));
    this.reproLimit=(int)(reproLimit*Math.pow(muteCoeff,((int)(Math.random()*(evo*2+1))-evo)));
    this.groundeat=(groundeat*Math.pow(muteCoeff,((int)(Math.random()*(evo*2+1))-evo)));
    
    this.xpos=xpos+(int)(Math.random()*(locVar*2+1))-locVar;
    this.ypos=ypos+(int)(Math.random()*(locVar*2+1))-locVar;
    
    int total=this.getBST();
    if (total>bstmax) {
    	this.reproLimit=(int)(5*total/(double)bstmax*rm);
    	this.size=(int)(this.size*total/(double)bstmax*sm);
    }
    
  }
  public boolean isAlive(){return this.alive;}
  public double getGroundEat() {
	  if(groundeat>1) {
		  return 1;
	  }
	  else if (groundeat<-1) {
		  return -1;
	  }
	  return this.groundeat;
  }
  public int getEnergy(){return this.energy;}
  public int getReproLimit(){return this.reproLimit;}
  public int randDeathChance() {return reproLimit+energy/500;}
  public int getX(){return this.xpos%MAX_X;}
  public int getY(){return this.ypos%MAX_Y;}
  public int getSize(){return this.size;}
  public double getStat(){return this.stat;}
  public double[] valchart() {
	  return this.valchart;
  }
  
  public int getBST() {return (int)(Math.abs(groundeat)*1000)*gm+reproLimit*rm+size*sm;}
  public void gainStat(int inc){stat+=inc;}
  public void resetStat() {stat=0;}
  public void gainEnergy(int inc) {

	  this.energy+=inc;
	  this.stat+=inc*bcc;
		
	}
  public void kill() {
	  this.alive=false;
  }
  
  
  
public void moveChar(int moveCoeff){
	if(moveCoeff>3 || moveCoeff<0) {
		System.out.println("error");
	}
	//System.out.println();
	//System.out.println();
	//System.out.println();
	//System.out.println();
	
    xpos=xpos+(moveCoeff+3)/2-2;//-1,0,0,1
    //System.out.print(moveCoeff);
    //System.out.print((moveCoeff+3)/2-2);
    //System.out.println(moveCoeff*2-((moveCoeff+3)/2-2)*3-3);
    ypos=ypos+moveCoeff*2-((moveCoeff+3)/2-2)*3-3;//0,-1,1,0
    
    if(xpos<0) {xpos=MAX_X+xpos;}
    if(xpos>=MAX_X) {xpos=xpos-MAX_X;}
    if(ypos<0) {ypos=MAX_Y+ypos;}
    if(ypos>=MAX_Y) {ypos=ypos-MAX_Y;}
    
    
  }
  public void changeStats(int scale) {

	    stat+=scale/(Math.abs(groundeat)+0.5);
	    energy-=size*bec+10;
  }

  
  public int move(int nxh, int pxh, int nyh, int pyh, int nxcs, int pxcs, int nycs, int pycs) {
	//nx, ny, py, px
	//left,up,down,right
	  double[] csi=new double[]{
			  conversion(nxcs),
			  conversion(nycs),
			  conversion(pycs),
			  conversion(pxcs)
			  };
	  double[] combine=new double[]{
			  csi[0]+nxh,
			  csi[1]+nyh,
			  csi[2]+pyh,
			  csi[3]+pxh
			  };
	  double[] finalmags=new double[] {
			  nxh*valchart[2]+csi[0]*valchart[3]+combine[0]*valchart[4],
			  nyh*valchart[2]+csi[1]*valchart[3]+combine[1]*valchart[4],
			  pyh*valchart[2]+csi[2]*valchart[3]+combine[2]*valchart[4],
			  pxh*valchart[2]+csi[3]*valchart[3]+combine[3]*valchart[4],
			  };
	  
	  double v=finalmags[0];
	  int m=0;
	  //System.out.print(" L ");
	  //System.out.print(v);
	  int n=1;
	  for (int x=1;x<4;x++) {
		  if(Math.abs(finalmags[x]-v)<0.0001) {
			  n+=1;
			  if(Math.random()<1.0/n) {
				  v=finalmags[x];
				  m=x;
			  }
		  }
		  else if(finalmags[x]>v) {
			  //System.out.print(x);
			  v=finalmags[x];
			  m=x;
		  }
		  /**
		  if(x==1) {
			  System.out.print(" R ");
		  }
		  if(x==2) {
			  System.out.print(" U ");
		  }
		  if(x==3) {
			  System.out.print(" D ");
		  }
		  System.out.print(finalmags[x]);
		  */
		  
		  
	  }
	  //System.out.println();
	  return m;
  }
  
  public double conversion(int sizes) {
	  double s=0;
	  if (sizes>this.size) {
		  s-=valchart[0];
	  }
	  else {
		  s=sizes*valchart[1];
	  }
		
	  return s;
  }
  
}
