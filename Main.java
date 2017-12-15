package latte;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.JXImagePanel;
 
 /*
  *  IProject project = ... // get some project resource
  *  IJavaProject javaProject = JavaCore.create(project);
  *  IClasspathEntry[] newClasspath = ...;
  *  javaProject.setRawClasspath(newClasspath, someProgressMonitor);
  *  
  *  
  *  //   		IMPLEMENT ADDING SHIFT WORDS TO NOOKS AS A LEMMA OF A DIFFERENT COLOR.
  *  //  		THEY MIGHT BE NUMEROUS FOR LARGER SEGMENTS. --> IS A SPECIAL TAGGING NECESSARY?
  *  //			IS ISOLATING SHIFT FORMS NECESSARY?
  *  
  *  			IMPLEMENT AUTOMATIC ADJUSTMENT OF UNIT LENGTH DEPENDING ON CONSONANT LENGTH
 */

public class AbsoluteLayoutDemo { 		//USAGE AS MODULE WOULD INCLUDE INPUT VARIABLES n, unitength, panellength, panelmargin, windowsize?
	
	static BufferedImage img;
	static String intake = "targetstring"; //
	static String disemvowelled = intake.replaceAll("[aeiouaiyɨɪɩʏeœɜəɛøæɶɑʌoʊɔɤuοοiɛöéèàêâô  ]", ""); //should j and w be filtered off?
    static int n = disemvowelled.length();
    static int scrollpanewidth = 1200;  //1300
    static int scrollpaneheight = 700; //700
    //max height in rows = n*(n+1)
    static int hmargin = 0;
    static int vmargin = 0;
    static int displayw = 600; //1350 for frame
    static int displayh = 400; //700
    //static int maxpanelw
    //static int maxunitlength
    static int adjustedlength = (displayw)/(2*n); //(displayw - (2 * hmargin))/(2*n)
    //static int adjustedlength = (scrollpanewidth - 2 * hmargin)/(2*n);
    static int unitlength = (adjustedlength > 100) ? 100 : adjustedlength; //adjustedlength
	//bpvfdtkgʒzsʃljmnŋɲɳrʁʤʥʦʧðþh
	//ijyɨɪɩʏeœɜəɛøæaɑɶɑʌoʊɔɔɤuwíáúøýéóö	
   // static int unitlength = 60; //100	//scrollpanewidth/2*n; // 60;  //80	
    static int adjustedwidth = (displayh + vmargin) / (12+1);
    static int panelwidth = (adjustedwidth > 40) ? 40 : adjustedwidth;
    static int panelmargin = 3; //should be a rounded fraction of the unitlength
    static int colspace = unitlength + panelmargin;
    
    static int panellength = unitlength; //default 65 //arbitrary variable name change?
    
    //WHEN CALCULATING PIXEL AREA FOR ROWS
    static int interrowmargin = panelmargin; //2, default intermargin
    static int rowspace = panelwidth + interrowmargin;
    //static int panelwidth = 16;  //32; //unitlength/3; //20; //default 14			scrollpaneheight-
    //static int minwidth = 120;
    
    //MISC. PANELS SETTINGS INIT.
    static int MagnifiableRange = 2 * unitlength;
    static int epwidth = 80;
    static int eplength = 300;
    
    
    
    private MouseEventListener mouseEventListener;
    
	public void setMouseEventListener(MouseEventListener listener) {
		this.mouseEventListener = listener;
	}
	
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
    	
    	/*
    	MouseListener ml = new MyMouseListener();
        frame.getGlassPane().addMouseListener(ml);
        frame.pack();
        frame.setVisible(true);
        frame.getGlassPane().setVisible(true);	*/
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
        	
            public void run() {
					try {
						createAndShowGUI();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }
        });
    }
    
    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            System.out.println(me);
        }
    }
	
	 private static void createAndShowGUI() throws SQLException, ClassNotFoundException, IOException {
	        //Create and set up the window.
	        JFrame frame = new JFrame("AbsoluteLayoutDemo");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     //   frame.setLayout(null);
	        Insets insets = frame.getInsets();
	        int fheight = 1350 + insets.left + insets.right;
	        int fwidth = 700 + insets.top + insets.bottom;
	        frame.setSize(fheight, fwidth);
	        //frame.setSize(1350 + insets.left + insets.right,
	        //              700 + insets.top + insets.bottom);
	        // frame.pack();
	        frame.setVisible(true);

	        //Set up the content pane.
	        addComponentsToPane(frame.getContentPane());
	    }
	 
    public static void addComponentsToPane(Container pane) throws SQLException, ClassNotFoundException, IOException {
        pane.setLayout(null);
        ((JComponent) pane).setOpaque(true);
        Color background = Color.darkGray;  //darkGray
        pane.setBackground(background);
        Insets insets = pane.getInsets();
        
        List<lemma> lemmas = Database.getResults(intake, 1);
        List<lemma> lemmas2 = Database.getResults(intake, 2);
        
        //lemmapack fw = Database.getResults(intake, 1);
       // List<lemma> lemmas = fw.lemmas;
        //int rowcount1 = fw.rowcount;
        int rowcount1 = Database.rowcount(1);
        
       // lemmapack bw = Database.getResults(intake, 2);
        //List<lemma> lemmas2 = bw.lemmas;
        int rowcount2 = Database.rowcount(2);
       
        int bwwidth = (rowcount1) * (panelwidth+panelmargin);
        int fwwidth = (rowcount2) * (panelwidth+panelmargin);
        int hpwidth = bwwidth + fwwidth + 40;
        System.out.println(hpwidth);
        
        
        
        
        int topmargin = 26; //26
        int leftmargin = 2;
        int c = 0; //left margin for centered display
   //     int scrollpanewidth = 1300;  //1300			added to static definitions
   //     int scrollpaneheight = 700;
        if 	(2*(n*unitlength) >= scrollpanewidth)	{c = 0;}
        else {c = (scrollpanewidth / 2) - (n * unitlength);}
        
        //SCRAPPING LEFT MARGIN?
        c = 0;
        int absoluteleftmargin = c; //c; //10 before
        
        //THIS DETERMINES THE 'START ROW' BY COUNTING PIXELS OF TOP SECTION ROWS
        int absoluteverticalcenter = (rowcount2*rowspace); // + topmargin; //scrollpaneheight / 2 + 100;  //700/2 = 350


int absolutehoriontalcenter = displayw /2;
int absolutetopmargin = 26;
     		   
        //VARIABLE TO MATCH AGAINST 'ROWPRINTCOUNTER+UL' TO DETERMINE CHARIOT RETURN
        int windowsize = c + 2*n*colspace; //1500; //2*n*colspace; //1365;
        int windowheight;
     
        //THIS IS NO LONGER AN EFFICIENT WORKAROUND NOW THAT THE NUMBER OF ROWS IS AVAILABLE --> REPLACED BY HPWIDTH?
        if (n > 10) {
       	 windowheight = rowspace*210; //*10*(10+1); = 110   210 at n = 20
        }
        else {
       	 windowheight = rowspace*n*(n+1);
        };
        
        
        
        
       // String disemvowelled = intake.replaceAll("[aeiouaiyɨɪɩʏeœɜəɛøæɶɑʌoʊɔɤuοοiɛöéèàêâô  ]", ""); //should j and w be filtered off?
       // int n = disemvowelled.length();
        
     
       // List<lemma> lemmas = Database.getResults(intake, 1);
       // List<lemma> lemmas2 = Database.getResults(intake, 2);
        //System.out.println(intake);
        //System.out.println(disemvowelled);
        //int displaywidth = n * unitlength;
        // int panelmargin = 1;								---> sent to static
        // int colspace = unitlength + panelmargin;  //panellength + panelmargin;
        
        JLayeredPane hostpanel = new JLayeredPane();   //--> make it a custom class with a setHeight method taking n and rowspace as parameters
     hostpanel.setBounds(50, 50, 100, 300); //pane.getWidth(), pane.getHeight());
        //hostpanel.add(Box.createRigidArea(new Dimension(0,26)));
        hostpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        hostpanel.setVisible(true);
        hostpanel.setOpaque(true);
        hostpanel.setBorder( new TitledBorder( "Hostpanel I" ));
        
        //ODDLY, THIS SCROLLS TO THE BOTTOM OF THE SCROLLPANE, PLUS IT ALLOWS x an y positioning
      //  hostpanel.setPreferredSize(new Dimension(200,200));  //900,1582 width,height
        //hostpanel.setPreferredSize(new Dimension(2000,2000));
        hostpanel.setBackground(Color.DARK_GRAY); //Color.yellow
        //pane.add(hostpanel);
        
      //SECOND HOSTPANEL NESTED IN THE FIRST
        JLayeredPane hostpanel2 = new JLayeredPane(); 
        
        //ONLY THE X AND Y SETTINGS WORK
        hostpanel2.setBounds(15, 15, 800, 600); //pane.getWidth(), pane.getHeight());
           //hostpanel.add(Box.createRigidArea(new Dimension(0,26)));
           hostpanel2.setLayout(null);
           hostpanel2.setVisible(true);
           hostpanel2.setOpaque(true);
           hostpanel2.setBorder( new TitledBorder( "Hostpanel II" ));
           
           //THIS IS THE EFFECTIVE SIZE SETTING
          hostpanel2.setPreferredSize(new Dimension(windowsize + 200,hpwidth + 100));  //900,1582 width,height
           //hostpanel.setPreferredSize(new Dimension(2000,2000));
           hostpanel2.setBackground(Color.GRAY); //Color.yellow
           hostpanel.add(hostpanel2);
        
       /* Rectangle epbounds = new Rectangle(100, 100, 200, 100);
        nookfullview epx = new nookfullview(epbounds);
        //epx.setBounds(50, 50, 200, 200);
        epx.setVisible(true);
        epx.nookpanel.setVisible(true);
        hostpanel.add(epx);
        */
        
        //SCROLLPANE SETBOUNDS
       
        
        //SCROLLPANE DEFINITION
        JScrollPane hostscrollpane = new JScrollPane(hostpanel);
        hostscrollpane.setVisible(true);
        hostscrollpane.setBounds(leftmargin, topmargin, scrollpanewidth, scrollpaneheight);//(absoluteleftmargin+(0*columnwidth), absolutetopmargin+(1*rowspace), 2*panellength+intermargin, panelwidth); 
    //    hostscrollpane.getViewport().setOpaque(false);
        //hostscrollpane.setBorder(grayline);//.setBorder( new TitledBorder( "My Titled Scroll Pane" ) );//.setBorder(title);
    //  hostscrollpane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
        //hostscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    //  hostscrollpane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        hostscrollpane.setBorder(null);
        hostscrollpane.setBackground(Color.red);
        pane.add(hostscrollpane);
        hostscrollpane.revalidate();
        //hostpanel.revalidate();
        
        //SCROLLPANE SCROLLBAR POLICIES
        if (n < 9) {
       	 hostscrollpane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
       	 hostscrollpane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
       	 hostscrollpane.revalidate();
        }
       else {};
       
       
          
       
     //APPEARS TO REMOVE SCROLLING AND SETS VIEWPORT START POSIITON TO TOP
       //hostpanel2.setPreferredSize(new Dimension(windowsize,windowheight));
       
       
       //int absoluteleftmargin = 10; 
      
        
       //OPTIONAL GRAPHICS INIT.
 		//img = ImageIO.read(new File("images/modelseat.jpg"));
 		//img = ImageIO.read(new File("images/country_icons/sun.png"));
        
        
      //List<lemma> lemmas = Database.getResults(consonants, n, counter123);à
			//from the List<massresults> which for each result has properties for nookid, word, en, pronunciation, pos and vowel segments
				//run a loop to find and copy every massresults[i] where the nookid = the current vault nookid (counter123 format)
 
        Font font = new Font("arial", Font.PLAIN, 10);
        Font font2 = new Font("arial", Font.PLAIN, 11);
        Font font3 = new Font("arial", Font.PLAIN, 11);
        Font fontp = new Font("arial", Font.PLAIN, 10);
        Color prop = Color.white;
        Color transparentpanelcolor = new Color(0,0,60); // pale blue for white bg --> new Color(0,0,100)

     //DELETE LATER
       /* hostpanel2.setPreferredSize(new Dimension(windowsize,windowheight));
     hostpanel2.revalidate();
     JPanel temp1 = new JPanel();
     JPanel temp2 = new JPanel();
     JPanel temp3 = new JPanel();
     hostpanel2.add(temp1);
     hostpanel2.add(temp2);
     hostpanel2.add(temp3);
     double zoompercent = 1;
     */
     
        
        int r, g, b;
        r = 0; g = 0; b = 255;
        
        // COLUMNPRINTCOUNTER INIT.
int columnprintcounter = absoluteleftmargin; //filled row space so far, to be matched with windowsize
        
int rowprintcounter = absoluteverticalcenter + rowspace; // + 20; //absolutetopmargin;
int rowprintcounter2 = absoluteverticalcenter - rowspace;
        int counter1 = 1;
        int counter2 = 1;
        int counter3 = 1;
        int counter2range; 
        int counter3range;
        int counteri = counter1;
        int counterii = counter2;
        int counteriii = counter3;
        int counter1range; 
        counter1range = 2*n;
        int i = 0;
        
        //NOOK ARRAYS-RELATED INIT.
        //int numberofnooks = n*(2*n+1);
        
							        int nookIndex = 0;
							        int epIndex = 2;
							        int tpIndex = 3;
        
        nook[] nook1 = new nook[n*(2*n+1)];
        nook[] nook2 = new nook[n*(2*n+1)];
     //   nook[] nook3 = new nook[n*(2*n+1)];
        
        transparentpanel[] tp1 = new transparentpanel[n*(2*n+1)];
        transparentpanel[] tp2 = new transparentpanel[n*(2*n+1)];
        
        NookMagnifier[] ep1 = new NookMagnifier[n*(2*n+1)];	//WAY TOO BIG
        NookMagnifier[] ep2 = new NookMagnifier[n*(2*n+1)];
        
        //LOOPING SYS. VARIABLE INIT.
        int cellid = 0;
        boolean deleteRowSignal1 = false; //supresses 'empty' rows. 0 = false, 1 = true
        boolean deleterowsignal2 = false;
        int rowcounter = 1; 
        int rowcounterrw = 1;
        int rowcounterfw = 1;
        
        if (n>=10) {
			counter1range = 20;
		}
		else {
			counter1range = 2*n;
		};
        
		//CORE NOOK.SETBOUNDS-SETTING LOOPS
        for (counter1 = 1; counter1 <= counter1range; counter1++) { 
        		panellength = counter1 * (unitlength + panelmargin) - panelmargin;
            if (counter1 < n+1){ 
                counter2range = counter1; 
            }
            else {
                counter2range = 2*n + 1 - counter1;
            };
            
            for (counter2 = 1; counter2 <= counter2range; counter2++) {
  columnprintcounter = absoluteleftmargin + ((counter2 - 1) * (unitlength + panelmargin)); //absoluteleftmargin + 
            	counter3 = 0;	//1
            	//counter3 = counter3 - 1;
            	counter3range = (windowsize - columnprintcounter) / (panelmargin + panellength);
            	
       ////////////////////////
       deleteRowSignal1 = true; //AS THIS IS THE 'NEW ROW JUNCTION', the row signal is turned off 'until further notice'.
       deleterowsignal2 = true; //even thought the print counter advances, this means 'do not add a row [print] until further notice'
       ////////////////////////
            	for (;columnprintcounter + panelmargin + panellength <= windowsize; columnprintcounter = columnprintcounter + panelmargin + panellength) {
            		counter3 = counter3 + 1;
            		
            		///// TP         
            		BigDecimal.ONE.add(new BigDecimal(Math.sqrt(5))).divide(new BigDecimal(2));	
            		double phi = 1.61803398874989484820458683436563811772030917980576286213544862270526046281890;
            		//int roundphi = (int) Math.round(phi);
            		int tplength = (int) Math.round(3.5*unitlength);
            		int tpwidth = (int) Math.round(tplength/phi);//74; //160
            		
///////////////    ///////////////    ///////////////    ///////////////    ///////////////    ///////////////    ///////////////    
//TO INSERT IT IN HP1 INSTEAD OF HP2, ADD HOP2 X POSITION TO 'Columnprintcounter'
//DO THAT FOR BOTH TPs AND EPs
///////////////    ///////////////    ///////////////    ///////////////    ///////////////    ///////////////    
            		Rectangle bounds = new Rectangle(columnprintcounter, rowprintcounter+panelwidth, tplength, tpwidth);
            		Rectangle bounds2 = new Rectangle(columnprintcounter, rowprintcounter2+panelwidth, tplength, tpwidth);
            		
            		tp1[i] = new transparentpanel(bounds);
            		tp2[i] = new transparentpanel(bounds2);
            		tp1[i].setVisible(false);
            		tp2[i].setVisible(false);

            		///// EP ("Extension panel" or "Nook Magnifier")
            		
            		Rectangle ep1bounds = new Rectangle(columnprintcounter, rowprintcounter - epwidth, eplength, epwidth);
            		Rectangle ep2bounds = new Rectangle(columnprintcounter, rowprintcounter2 - epwidth, eplength, epwidth);
            		
            		if (panellength <= MagnifiableRange) {
                    	ep1[i] = new NookMagnifier(ep1bounds);
                    	ep2[i] = new NookMagnifier(ep2bounds);
                    	ep1[i].setVisible(false);
                		ep2[i].setVisible(false);
                  }
            		
            		nook1[i] = new nook();
            		nook2[i] = new nook();
            		
            		//tp[i].setAlpha(.8f);
            		//ep[i].setVisible(true);
  //img          	
            		//nook1[i].setImg(img);
            		nook1[i].magnifiableRange = MagnifiableRange;
            		nook1[i].nookpanel.setBounds(columnprintcounter, rowprintcounter, panellength, panelwidth);
    nook1[i].setBounds(columnprintcounter, rowprintcounter, panellength, panelwidth);
            		nook1[i].setBackground(new Color(r, g, b));  //setBackground(Color.orange);
           nook1[i].setVisible(false);
            		String ranges = String.format("%02d", 2*n) + " " + String.format("%02d", counter2range) + " " + String.format("%02d", counter3range);
            		String nookid = String.format("%02d", counter1) + String.format("%02d", counter2) + String.format("%02d", counter3);
            		nook1[i].setnookid(nookid); //(List<lemma> lemmas)
            		nook1[i].setcounters(counter1, counter2, counter3);
            		nook1[i].setranges(2*n, counter2range, counter3range);
            		nook1[i].nookscrollpane.setOpaque(false);
            		
            		//nook2[i].setImg(img);
            		nook2[i].nookpanel.setBounds(columnprintcounter, rowprintcounter2, panellength, panelwidth);
    nook2[i].setBounds(columnprintcounter, rowprintcounter2, panellength, panelwidth);
            		nook2[i].setBackground(new Color(r, g, b));  //setBackground(Color.orange);
            		nook2[i].setVisible(true);
            		nook2[i].setnookid(nookid); //(List<lemma> lemmas)
            		nook2[i].setcounters(counter1, counter2, counter3);
            		nook2[i].setranges(2*n, counter2range, counter3range);
            		nook2[i].nookscrollpane.setOpaque(false);
            	    JLabel nkid = new JLabel(nookid + " " + rowcounter);
            	    nkid.setFont(font);
            	    JLabel nkid2 = nkid;
            	//    nook1[i].nookpanel.add(nkid);
            	//    nook2[i].nookpanel.add(nkid2);
            	    
            	    
            	    
            	    									//apparently for the nook1 range this is the 'first cell' pass
            	    		//if a previous valid nook (see further signal section) in the row has "turned the signal ON", this adds the present nook.
            	    		//but it's NOT NECESSARILY VISIBLE
               												if (deleteRowSignal1 == false){
               															hostpanel2.add(nook1[i], new Integer(nookIndex)); 
               															//hostpanel2.setComponentZOrder(nook1[i], nookIndex);
               															//index 6?
               															hostpanel2.add(nook2[i], new Integer(nookIndex));
               															//hostpanel2.setComponentZOrder(nook2[i], nookIndex);
               															//index 7?
               												}
               												else {};

               		//first full loop throught results										
            		for (int ii = 0; ii < lemmas.size(); ii++) {		
            			lemma lemmaob = lemmas.get(ii);

            			//////////////////////////////////////////////////////////////////////////////////////
            			if (lemmaob.nookid.equals(nookid)) {		//'if it's in the list, if it contains anything ...'
            			//////////////////////////////////////////////////////////////////////////////////////	
            				nook1[i].setVisible(true);				//'... then only is it supposed to be visible'
            				hostpanel2.add(tp1[i], new Integer(tpIndex));
            				
            											//SPECIAL CASE WHEN this is the first valid nook of the row, e.g. new line
            											if (deleteRowSignal1 == true){ 		//if the "signal" is "OFF"
            													hostpanel2.add(nook1[i], new Integer(nookIndex), 1);
            													//hostpanel2.setComponentZOrder(nook1[i], nookIndex);
            													//at this point there simply isn't enough components (0) in the container for an index of 2.
            											}
            											else {};

          nook1[i].addnooklemma(lemmaob);
          nook1[i].nookscrollpane.setPreferredSize(new Dimension(panellength, panelwidth)); //60, 30

          
          final transparentpanel jxpanel = tp1[i]; //ppanel 											
            											
           //final int difference = (first_var > second_var) ? first_var - second_var : second_var - first_var;
   
          
 /**/       final NookMagnifier jxpep1 = (ep1[i] != null) ? ep1[i] : null;
            											
            //final nookfullview jxpep1;
            //jxpep1 = null;
            				if (ep1[i] != null) { //(if (ep1[i] != null) { //panellength <= MinimumVisibleLength
            					ep1[i].addnooklemma(lemmaob);
            					hostpanel2.add(ep1[i], new Integer(epIndex), 0);
            					//hostpanel2.setComponentZOrder(ep1[i], epIndex);
            					//ep1[i].setVisible(true);
            					ep1[i].setMouseEventListener(new MouseEventListener(){

            					@Override
            					public void passLemma(lemma lemmaob) {
            						try {
            							jxpanel.setcontents(lemmaob);
            						} catch (IOException e) {
            							e.printStackTrace();
            						} // <-- nope, this is done by the new jx2panel, who must have the addlemma method
            						jxpanel.setVisible(true);
            					}

            					@Override
            					public void passDomain(String domain) {}

								@Override
								public void psPt(Point pt) {
									if (jxpep1 != null) {
									if (pt.x+jxpep1.getX() >= jxpep1.getX() && pt.x+jxpep1.getX() <= jxpep1.getX()+jxpep1.getWidth()
											&& pt.y+jxpep1.getY() <= jxpep1.getY()+jxpep1.getHeight())
									{}
									else {};
									};
								}

								@Override
								public void tpDisappear() {
									jxpanel.setVisible(false);
								}

								@Override
								public void epInvisible() {
									if (jxpep1 != null) {
										jxpep1.setVisible(false);
										System.out.println("ep1 event epInvisible");
									}
								}

								@Override
								public void epVisible() {
									if (jxpep1 != null) {
										jxpep1.setVisible(true);
										System.out.println("ep1 event epVisible");
									}
									
								}

								@Override
								public void tpShow() {
									jxpanel.setVisible(true);
								}

            				});
            					
            					//jxpep1 = ep1[i];
            					//nook1[i].addnooklemma2(lemmaob);
            					//ep[i] = new transparentpanel(bounds);
            				}
            				else {
            					//System.out.println("??????");
            					//nook1[i].addnooklemma(lemmaob);
            				}

            				
            				final nook nookobject = nook1[i];
            				
            				nook1[i].setMouseEventListener(new MouseEventListener(){

            					@Override
            					public void passLemma(lemma lemmaob) {
            						try {
            		jxpanel.setcontents(lemmaob);
            						} catch (IOException e) {
            							e.printStackTrace();
            						} 
            						jxpanel.setVisible(true);
            					}

            					@Override
            					public void passDomain(String domain) {}

								@Override
								public void psPt(Point pt) {
									if (pt.x+nookobject.getX() >= nookobject.getX() && pt.x+nookobject.getX() <= nookobject.getX()+nookobject.getWidth()
											&& pt.y+nookobject.getY() <= nookobject.getY()+nookobject.getHeight()){}
									else {};
									//System.out.println("nookobject.getX():" + nookobject.getX() + " pt.x" + pt.x);
									//System.out.println("nookobject.getY():" + nookobject.getY() + " pt.y" + pt.y);
								}

								@Override
								public void tpDisappear() {
									jxpanel.setVisible(false);
								}

								@Override
								public void epInvisible() {
									if (jxpep1 != null) {
										jxpep1.setVisible(false);
										System.out.println("nook1 event epInvisible");
									}
								}

								@Override
								public void epVisible() {
									if (jxpep1 != null) {
										jxpep1.setVisible(true);
										System.out.println("nook1 event epVisible");
									}
									
								}

								@Override
								public void tpShow() {
									jxpanel.setVisible(true);
								}

            				}); //nook1[i].setMouseEventListener(new MouseEventListener(){

            				nook1[i].revalidate();
            				deleteRowSignal1 = false;	//Because the present loop issues from a 'match', the row signal is activated
            											//Meaning the row is not deleted?
            				
            			}	//if (lemmaob.nookid.equals(nookid)) {
            			
            			
            			
            		}	//for (int ii = 0; ii < lemmas.size(); ii++) {
            		
           ////////////////////////			//////////////////////// 		 //////////////////////// 		 //////////////////////// 	
            		
            		for (int iii = 0; iii < lemmas2.size(); iii++) {
            			lemma lemmaob2 = lemmas2.get(iii);

            			if (lemmaob2.nookid.equals(nookid)) {
            				nook2[i].setVisible(true);
            				hostpanel2.add(tp2[i], new Integer(tpIndex));
            				//hostpanel2.setComponentZOrder(tp2[i], tpIndex);
            				
            															if (deleterowsignal2 == true){
            																hostpanel2.add(nook2[i], new Integer(nookIndex), 1);
            																//hostpanel2.setComponentZOrder(nook2[i], nookIndex);
            																//index 5??
            															};
            				
            				nook2[i].addnooklemma(lemmaob2);
            				nook2[i].nookscrollpane.setPreferredSize(new Dimension(panellength, panelwidth));
            				
            				final nook nookobject2 = nook2[i];
            				final transparentpanel jxpanel2 = tp2[i]; //ppanel
            				//final transparentpanel jxpanel1 = ep1[i];
            				
            				nook2[i].setMouseEventListener(new MouseEventListener(){

                    					@Override
                    					public void passLemma(lemma lemmaob2) {
                    						try {
                    							jxpanel2.setcontents(lemmaob2);
                    						} catch (IOException e) {
                    							e.printStackTrace();
                    						} 
                    						jxpanel2.setVisible(true);
                    					}

                    					@Override
                    					public void passDomain(String domain) {
                    					}

        								@Override
        								public void psPt(Point pt) {
        									if (pt.x+nookobject2.getX() >= nookobject2.getX() && pt.x+nookobject2.getX() <= nookobject2.getX()+nookobject2.getWidth()
        											&& pt.y+nookobject2.getY() <= nookobject2.getY()+nookobject2.getHeight()){}
        									else {};
        									//System.out.println("nookobject.getX():" + nookobject.getX() + " pt.x" + pt.x);
        									//System.out.println("nookobject.getY():" + nookobject.getY() + " pt.y" + pt.y);
        								}

        								@Override
        								public void tpDisappear() {
        									jxpanel2.setVisible(false);
        								}

										@Override
										public void epInvisible() {
											System.out.println("nook2 event epInvisible");
										}

										@Override
										public void epVisible() {
											System.out.println("nook2 event epVisible");
										}

										@Override
										public void tpShow() {
											jxpanel2.setVisible(true);
										}

                    				}); //nook2[i].setMouseEventListener(new MouseEventListener(){
            				
            				nook2[i].revalidate();
            				deleterowsignal2 = false;	//row not empty = row signal on
            				
            				
            			} //if (lemmaob2.nookid.equals(nookid)) {
            			
            			
            			
    				} //for (int iii = 0; iii < lemmas2.size(); iii++) {
            		
            		i = i + 1;
            		
            	};	//closing bracket for "for (;columnprintcounter + panelmargin + panellength <= windowsize; columnprintcounter = columnprintcounter + panelmargin + panellength) {
        			//counter3 = counter3 + 1"; 
            	
            	//rowcounter = rowcounter + 1;
            	if (deleteRowSignal1 == true) {		//no row increment added
            		rowprintcounter = rowprintcounter;
            		rowprintcounter2 = rowprintcounter2;
            	}
            	else {		//if deleterowswitch = false, then counter goes + 1
            		rowprintcounter = rowprintcounter + rowspace;  
            		rowcounterfw = rowcounterfw + 1;
            		rowprintcounter2 = rowprintcounter2 - rowspace;
            		rowcounterrw = rowcounterrw + 1;
            	};
            	
            }; //closing bracket of "for (counter2 = 1; counter2 <= counter2range; counter2++) {"

            int step;
            if (n>10){
            	if (counter1 <= 20){
            		step = Math.round(255/(20));
            		r = r + step;
            		g = g + 6;
            		b = b - step;
            	}
            	else {

            	}
            }
            else {
            	step = Math.round(255/(2*n));
            	r = r + step;
            	g = g + 6;
            	b = b - step;
            }

        } //closing bracket of "for (counter1 = 1; counter1 <= counter1range; counter1++) { "
        
       
        /*
       for (i=0; i<=nook1.length-1; i++) //nook2.length-1(n*(2*n+1))
    	   { 		
    	   System.out.println("Layer 0 : " + hostpanel2.getComponentCountInLayer(0));
    	   System.out.println("nook1[" + i + "] : " + hostpanel2.getComponentZOrder(nook1[i]));
    	   System.out.println("nook2[" + i + "] : " + hostpanel2.getComponentZOrder(nook2[i]));
    	   System.out.println("Layer 1 : " + hostpanel2.getComponentCountInLayer(1));
    	   System.out.println("ep1[" + i + "] : " + hostpanel2.getComponentZOrder(ep1[i]));
    	   System.out.println("ep2[" + i + "] : " + hostpanel2.getComponentZOrder(ep2[i]));
    	   System.out.println("Layer 2 : " + hostpanel2.getComponentCountInLayer(2));
    	   System.out.println("tp1[" + i + "] : " + hostpanel2.getComponentZOrder(tp1[i]));
    	   System.out.println("tp2[" + i + "] : " + hostpanel2.getComponentZOrder(tp2[i]));
    	   
    	// hostpanel2.setComponentZOrder(nook1[i], nookIndex);     	
        // hostpanel2.setComponentZOrder(nook2[i], nookIndex);
        //hostpanel2.setComponentZOrder(ep1[i], 1);
        //hostpanel2.setComponentZOrder(ep2[i], 1);
        //hostpanel2.setComponentZOrder(tp1[i], 0);
        //hostpanel2.setComponentZOrder(tp2[i], 0);
    	   }; 
		*/
        
        
        lemmas = null;
		lemmas2 = null;
		
        int topheight = rowcounterrw * rowspace;
        int botheight = rowcounterfw * rowspace;
        int viewheight = topheight + botheight;
        //int a = pane.get

        //System.out.println("http://marketplace.eclipse.org/content/mbprofiler-0nook2 array size:" + nook2.length);
       // System.out.println("number of nooks:" + cellid);
        
        /*
        		int y = 0;
        		for (i=0; i<=nook2.length-1; i++)
        		{
        		   if (nook2[i]!=null) { y = y + 1; } 
        		    if (nook2[i].nookpanel.isVisible() == true) { y = y + 1; }
        		}
        		//System.out.println("used size of nook2 array:" + y);
        		
    			//System.out.println("nook2 array size:" + nook1.length);
   		*/
    			
    			/*	String sumupto99 = "";

    	    	for (i=11;i<=99;i++) {
    	    		int d = i;
    	    		sumupto99 = sumupto99 + String.valueOf(d) ;
    	    	}
    	    	System.out.println(sumupto99);	

    			 */
    	    	
    			
    		//	for (i=0; i==nook1.length-1; i++){
        	/*	
    				int c1 = nook1[i].counter1;
    				int c2 = nook1[i].counter2;
    				int c3 = nook1[i].counter3;
    				int r1 = 2*n;
    				int r2 = nook1[i].counter2range;
    				int r3 = nook1[i].counter3range;
    		*/	
    			/*
    			
    				for (counter1=1; counter1<=2*n; counter1++){
    					if (counter1 < n+1){ 
    		                counter2range = counter1; 
    		            }
    		            else {
    		                counter2range = 2*n + 1 - counter1;
    		            };
    					
    					for (counter2=1; counter2<=counter2range; counter2++){
    						counter3range = (windowsize - ((counter2 - 1)*(unitlength + panelmargin)))/(counter1*(unitlength + panelmargin));
    						
    						for (counter3=1; counter3<=counter3range; counter3++){
    							
    							for (i=0; i==nook1.length-1; i++){ //-1 ?
    								if (nook1[i].counter1 == counter1  && nook1[i].counter2 == counter2 && nook1[i].counter3 == counter3 ){
    									if (nook1[i].nookpanel.getComponents() == null){
    										for (int j=i; j<=nook1.length-1; j++){ //-1?
    		    								int actualrow = nook1[j].getY();
    		    								int x = nook1[j].getX();
    		    								int width = nook1[j].getWidth();
    		    								int height = nook1[j].getHeight();
    		    								int newrow = actualrow - 15; //rowspace
    		    							//	nook1[j].setBackground(Color.red);
    		    							//	hostpanel2.add(nook1[j]);
    		    							  //  nook1[j].nookpanel.setLocation(x, newrow);
    		    							  //  nook1[j].setBounds(x, newrow, width, height);
    		    							 nook1[j].setVisible(false);
    		    							    hostpanel2.repaint();
    		    							    hostpanel2.validate();
    		    							}
    	    							}
    								}
    							}
    						}		//end counter3 loop
    						
    					/*	if (eraserow = true){
    							
							}; 
						
    					}
    				}
        	}
    			 	
*/
        
    //  LITTLE BOX SHOWING CONSONANTS  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        Border title, grayline;
        grayline = BorderFactory.createLineBorder(new Color(107, 106, 104));
        title = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(
                        EtchedBorder.LOWERED, new Color(107, 106, 104)
                        , Color.GRAY
                        ), "no vowels", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Arial",Font.PLAIN,7), Color.white);
        
        JLabel disemvowelled2 = new JLabel();
        disemvowelled2.setText(intake);
        disemvowelled2.setBounds(4, 0, 150, 26); //65,26
        disemvowelled2.setForeground(Color.white); //Alt. Blue
        disemvowelled2.setVisible(true);
        disemvowelled2.setBorder(title); 
        pane.add(disemvowelled2);
    
        
    //  REFERENCE CHARACTER DISPLAY  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        int calllettersposition = unitlength-1; //changed from -3
        int verticalposition = absoluteverticalcenter; //6;
        int halfunit = Math.round(unitlength/2);
        JLabel[] labeldots = new JLabel[n];
        JLabel[] letters = new JLabel[n];
        int dotprintcounter = c+ (2*unitlength) + panelmargin; //leftmargin+ (2*unitlength) + panelmargin;
        i = 0;
        
 //for (;dotprintcounter + (2*unitlength) + panelmargin <= windowsize +200; dotprintcounter = dotprintcounter + (2*unitlength) + 2*panelmargin){
        for (i = 0;i<=n-1;i++){
        //(i = 2; i <= 14; i = i = 2) {
        	labeldots[i] = new JLabel("·");
        	labeldots[i].setBounds(dotprintcounter-3, verticalposition+2, 6, 10);
        	labeldots[i].setHorizontalTextPosition(0);
        	labeldots[i].setFont(font3);
        	labeldots[i].setForeground(Color.white); //Color.yellow // grayish yellow -> (178,150,0) light-gray -> (175,175,175)
        	labeldots[i].setVisible(true);
        	hostpanel2.add(labeldots[i], new Integer(3));
        	
        	letters[i] = new JLabel();
            String letter = disemvowelled.substring(i,i+1);
            letters[i].setText(letter);
            letters[i].setFont(font);
            letters[i].setForeground(Color.white);
            letters[i].setBounds(dotprintcounter-unitlength-5, verticalposition, 8, 15);
            letters[i].setVisible(true);
            hostpanel2.add(letters[i], new Integer(3));
            dotprintcounter = dotprintcounter + (2*unitlength) + 2*panelmargin;
                   //System.out.println(letters[j].getX() + " " + letters[j].getY());
      //i = i + 1;
        	//dotprintcounter = dotprintcounter + (2*unitlength) + panelmargin;
        }
        
        //WHAT IS THIS?
   // Rectangle aRect = new Rectangle(0, letters[1].getY() - (windowheight/2), 2*n*colspace, windowheight);
   //letters[1].scrollRectToVisible(aRect);
        //int windowsize = c + 2*n*colspace; //1500; //2*n*colspace; //1365;windowheight = rowspace*n*(n+1);
        
        letters = null;
        labeldots = null;
        
/*
        y = 0;
   		for (i=0; i<=letters.length-1; i++)
   		{
   		    if (letters[i]!=null) { y = y + 1; } 
   		}
   		System.out.println("used size of letters array:" + y);
   		System.out.println("size of letters array:" + letters.length);
   		System.out.println("calculated number of nooks " + n*(2*n+1));
   		*/
   		nook1 = null;
   		nook2 = null;
   		tp1 = null;
   		tp2 = null;
   		ep1 = null;
   		ep2 = null;
   		
   		/*
   		JXImagePanel ladybg = new JXImagePanel();
   		ladybg.setImage(ImageIO.read(new File("images/rapunzel.jpg")));
   		ladybg.setBounds(100, 26, 1440, 600);
   		ladybg.setVisible(true);
   		//hostpanel2.add(ladybg);
   		
   		JLabel wordintake = new JLabel();
   		wordintake.setText(intake);
   		wordintake.setBounds(4, 0, 150, 26); //65,26
   		wordintake.setForeground(Color.white); //Alt. Blue
   		wordintake.setVisible(true);
   		ladybg.add(wordintake);
   		
        FrameA frm = new FrameA();
        frm.setBounds(150, 150, 1200, 500);
        frm.setVisible(true);
        //frm.setLayout(null);
         */
    }
    
}
