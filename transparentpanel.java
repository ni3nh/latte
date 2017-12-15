package latte;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.JXPanel;

public class transparentpanel extends JXPanel {
	JPanel foregroundPanel = new JPanel();
		JPanel positionercontainer = new JPanel(); //3 vertical parts
			JPanel upper_shelf = new JPanel(); //topcontainer
				JLabel prop1 = new JLabel();
				JLabel prop2 = new JLabel();
				JLabel prop3 = new JLabel();
			JPanel in_between_shelf = new JPanel(); 
					JTextArea definitionTextArea = new JTextArea();
					JScrollPane definitionTextAreaScrollpane = new JScrollPane(definitionTextArea);
			JPanel lower_shelf = new JPanel();					//where the edit button is
				JLabel domains = new JLabel();
	//SPECIAL CLASS
	JPanel backgroundpanel = new backgroundlayer();
	 //contains flag, lemma and "ipa" translation
	BufferedImage img;
    
	
	String path = null;
	Font font = new Font("arial", Font.PLAIN, 10);
	Font font2 = new Font("courier", Font.PLAIN, 12);
	Font TopContainerFont = new Font("arial", Font.PLAIN, 12);
	Font fontp = new Font("arial", Font.PLAIN, 10);
	Font titlefont = new Font("arial", Font.PLAIN, 10);
	Font DefinitionFont = new Font("verdana", Font.PLAIN, 11);
	
	Color transparentpanelcolor = Color.blue;
	Color prop = Color.white;
	
	JLabel prop4 = new JLabel();
	
	JLabel prop6 = new JLabel();
	JLabel prop7 = new JLabel();
	JLabel prop8 = new JLabel();
	JLabel prop9 = new JLabel();
	JLabel prop10 = new JLabel();
	BufferedImage myPicture;
	JLabel langsign = new JLabel();
	ImageIcon myicon = new ImageIcon();
	
	private MouseEventListener mouseEventListener;

	public void setMouseEventListener(MouseEventListener listener) {
		   this.mouseEventListener = listener;
	}
	
	public transparentpanel(Rectangle bounds) throws IOException { //, Dimension dimension
		//setAlpha(.8f);
		setBounds(bounds);
		foregroundPanel.setBounds(0,0,this.getWidth(),this.getHeight());
		foregroundPanel.setVisible(true);
		foregroundPanel.setOpaque(false);
		foregroundPanel.setLayout(new BoxLayout(foregroundPanel, BoxLayout.Y_AXIS));
		foregroundPanel.setBackground(new Color(0,0,80,5));
		
		backgroundpanel.setBounds(0,0,this.getWidth(),this.getHeight());
		backgroundpanel.setVisible(true);
		backgroundpanel.setOpaque(false);
		
		/*
		 * foregroundpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		upper_shelf.setBorder(BorderFactory.createLineBorder(Color.green));
		in_between_shelf.setBorder(BorderFactory.createLineBorder(Color.yellow));
		lower_shelf.setBorder(BorderFactory.createLineBorder(Color.red));
		definitiontextarea.setBorder(BorderFactory.createLineBorder(Color.blue));
		*/
		
		definitionTextArea.setFont(DefinitionFont); //font2 //new Font("Serif", Font.ROMAN_BASELINE, 12));
		definitionTextArea.setLineWrap(true);
		definitionTextArea.setWrapStyleWord(true);
		definitionTextArea.setEditable(false);
		definitionTextArea.setVisible(true);
		//description.setRows(3);
		definitionTextArea.setForeground(Color.white);
		//description.setBackground(Color.black);
		definitionTextArea.setOpaque(false);
		
		
		setBorder(BorderFactory.createLineBorder(Color.white));
		//backgroundpanel.setBackground(new Color(0,0,80,5));
		add(foregroundPanel);
		add(backgroundpanel);
		//setBackground(transparentpanelcolor);
		setVisible(true);
		//setPreferredSize(new Dimension(dimension));
		setOpaque(false);
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(null);
		prop1.setForeground(prop);
		prop1.setFont(TopContainerFont);  //font2
		
		prop2.setForeground(prop);
		prop2.setFont(TopContainerFont);
		prop3.setForeground(prop);
		prop3.setFont(TopContainerFont);
		prop4.setForeground(prop);
		prop4.setFont(font2);
		domains.setForeground(prop);
		domains.setFont(font2);
			domains.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		prop6.setForeground(prop);
		prop6.setFont(font2);
		prop7.setForeground(prop);
		prop7.setFont(font2);
		prop8.setForeground(prop);
		prop8.setFont(font2);
		/*
		BufferedImage gla = ImageIO.read(new File("images/COUNTRY_ICONS/VATICAN-CITY.PNG"));
		BufferedImage gfr = ImageIO.read(new File("images/COUNTRY_ICONS/FRANCE.PNG"));
		BufferedImage gru = ImageIO.read(new File("images/COUNTRY_ICONS/RUSSIA.PNG"));
		BufferedImage gen = ImageIO.read(new File("images/COUNTRY_ICONS/UNITED-KINGDOM.PNG"));
		 */
		BufferedImage ggr = ImageIO.read(new File("images/country_icons/Vatican-City.png"));
		
		JLabel langsign = new JLabel();
		Dimension imagedimension = new Dimension(20,40);
		langsign.setPreferredSize(imagedimension);
		myicon.setImage(ggr);
		langsign.setIcon(myicon);
		langsign.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		upper_shelf.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		upper_shelf.setAlignmentY(JComponent.TOP_ALIGNMENT);
		upper_shelf.setLayout(new BoxLayout(upper_shelf, BoxLayout.X_AXIS));
		upper_shelf.setVisible(true);
		upper_shelf.setOpaque(false);
		//upper_shelf.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		upper_shelf.add(Box.createRigidArea(new Dimension(10,10)));
		upper_shelf.add(langsign);
			langsign.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		upper_shelf.add(prop1);
		upper_shelf.add(prop2);
		upper_shelf.add(prop3);
	
		 TitledBorder topBorder2 = BorderFactory.createTitledBorder("Definition");
		    topBorder2.setTitlePosition(TitledBorder.TOP);
		TitledBorder topBorder = new TitledBorder(new LineBorder(Color.white, 1),
        "Definition");
		topBorder.setTitleColor(Color.white);
		topBorder.setTitleFont(titlefont);
		
		//positionercontainer.setAlignmentY(Component.CENTER_ALIGNMENT);
		//positionercontainer.setAlignmentX(Component.LEFT_ALIGNMENT);
		//positionercontainer.setLayout(new BoxLayout(positionercontainer, BoxLayout.Y_AXIS));
		//positionercontainer.setVisible(true);
		//positionercontainer.setOpaque(false);
		//foregroundpanel.add(Box.createRigidArea(new Dimension(15,15)));
		foregroundPanel.add(upper_shelf);
		//positionercontainer.add(Box.createRigidArea(new Dimension(15,15)));
		//positionercontainer.add(upper_shelf);
		//positionercontainer.add(Box.createRigidArea(new Dimension(15,15)));
		definitionTextArea.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
		//JScrollPane dc = new JScrollPane(definitiontextarea);
		definitionTextAreaScrollpane.setVisible(true);
		definitionTextAreaScrollpane.getViewport().setOpaque(false);
		definitionTextAreaScrollpane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
       // dc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        definitionTextAreaScrollpane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        definitionTextAreaScrollpane.setBorder(null);
        //dc.revalidate();
        definitionTextAreaScrollpane.setBackground(Color.darkGray);
        definitionTextAreaScrollpane.setOpaque(false);
        MouseListener ml = new MyMouseListener();
        definitionTextAreaScrollpane.addMouseListener(ml);
     
		
		/*dc.setVisible(true);
        dc.setBounds(leftmargin, topmargin, scrollpanewidth, scrollpaneheight);//(absoluteleftmargin+(0*columnwidth), absolutetopmargin+(1*rowspace), 2*panellength+intermargin, panelwidth); 
        dc.getViewport().setOpaque(false);
        dc.setBorder(grayline);//.setBorder( new TitledBorder( "My Titled Scroll Pane" ) );//.setBorder(title);
    //  dc.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
        dc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        dc.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        dc.setBorder(null);
        in_between_shelf.add(dc);
        dc.revalidate();
        */
		
		in_between_shelf.setLayout(new BoxLayout(in_between_shelf, BoxLayout.X_AXIS));
		in_between_shelf.setVisible(true);
		in_between_shelf.setOpaque(false);
		in_between_shelf.add(Box.createRigidArea(new Dimension(10,10)));
		//in_between_shelf.add(definitiontextarea);
  in_between_shelf.add(definitionTextAreaScrollpane);
		in_between_shelf.add(Box.createRigidArea(new Dimension(10,10)));
		in_between_shelf.setPreferredSize(new Dimension(this.getWidth(), 30));
		//definition_row.setBounds(5, 30, this.getWidth()-10, 40);
		in_between_shelf.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		in_between_shelf.setAlignmentY(JComponent.CENTER_ALIGNMENT);

		lower_shelf.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		lower_shelf.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
		lower_shelf.setLayout(new BoxLayout(lower_shelf, BoxLayout.X_AXIS));
		lower_shelf.setVisible(true);
		lower_shelf.setOpaque(false);
		
		lower_shelf.add(Box.createRigidArea(new Dimension(10,10)));
		lower_shelf.add(domains);
		//positionercontainer.add(Box.createRigidArea(new Dimension(1,1)));
			//description.setBorder(BorderFactory.createLineBorder(Color.blue));
			//domains.setBorder(BorderFactory.createLineBorder(Color.pink));
			//positionercontainer.setBorder(BorderFactory.createLineBorder(Color.GREEN));
	//	description.setBorder(topBorder);
		//topBorder.setTitle("domains");
	//	domains.setBorder(topBorder);
		//positionercontainer.add(definitiontextarea);  
		//positionercontainer.add(in_between_shelf); 
		//positionercontainer.add(lower_shelf); //domains
		
		//foregroundpanel.add(positionercontainer);
		foregroundPanel.add(in_between_shelf);
		foregroundPanel.add(lower_shelf);
		foregroundPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		//foregroundpanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		revalidate();
		
		/*
		foregroundpanel.add(Box.createRigidArea(new Dimension(1,1)));
		foregroundpanel.add(langsign);
		foregroundpanel.add(Box.createRigidArea(new Dimension(1,1)));
        foregroundpanel.add(prop1);
        foregroundpanel.add(Box.createRigidArea(new Dimension(1,1)));
        foregroundpanel.add(prop2);
        foregroundpanel.add(Box.createRigidArea(new Dimension(1,1)));
        foregroundpanel.add(prop3);
        foregroundpanel.add(Box.createRigidArea(new Dimension(1,1)));
        foregroundpanel.add(description);  //def
        foregroundpanel.add(Box.createRigidArea(new Dimension(1,1)));
        foregroundpanel.add(prop5);
        foregroundpanel.add(Box.createRigidArea(new Dimension(1,1)));
       */
	
	}
	
	public void setcontents(lemma lemmaob) throws IOException {
		addMouseListener(new Adapter());
		///////////////////////////////////////////////////////////////////
		//long eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK;
		
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
		    @Override
		    public void eventDispatched(AWTEvent awte) {//all mouse events will be processed here you will have to check for the mouse events you are interested in
	//	    System.out.println(awte);
		    }
			}, AWTEvent.MOUSE_EVENT_MASK);//for Mouse events only
			///////////////////////////////////////////////////////////////////
			prop1.setText(" " + lemmaob.lemma);
			prop2.setText(" [" + lemmaob.ipa + "]");
			prop3.setText(" " + lemmaob.pos);
			//this.prop4.setText(lemmaob.definition);
			definitionTextArea.setText(lemmaob.definition);
			definitionTextArea.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				JTextArea textarea = (JTextArea)e.getSource();
				//JScrollPane dc1 = (JScrollPane)e.getSource();
				//dc1.getParent().getParent().getParent().setVisible(true);
				//dc1.getParent().getParent().getParent().dispatchEvent(e);
				textarea.getParent().getParent().getParent().getParent().setVisible(true); //one getParent() added for dc
				textarea.getParent().getParent().getParent().getParent().dispatchEvent(e);
				//backgroundpanel.dispatchEvent(e);
	//			System.out.println("Mouse entered jtextarea --> 4 levels, event dispatched");
	//			System.out.println(textarea.getParent().getParent().getParent().getParent().getClass());
	//			System.out.println("Mouse entered jtextarea --> 3 levels, event dispatched");
	//			System.out.println(textarea.getParent().getParent().getParent().getClass());
			}
			public void mouseExited(MouseEvent e) {									//event fired upon actual "mouse over" on jscrollpane
				JTextArea textarea = (JTextArea)e.getSource();
			//	textarea.setVisible(true);
	//x			mouseEventListener.tpShow();
				//JScrollPane dc1 = (JScrollPane)e.getSource();
			//	textarea.getParent().getParent().getParent().getParent().dispatchEvent(e);
				System.out.println("Mouse exited JTextArea, event dispatched");				//containment hierarchy: JViewport, JScrollPane, JPanel, JPanel, transparentpanel
			//	System.out.println("textarea.getParent(): " + textarea.getParent()); 			//javax.swing.JViewport[,0,0,190x80,layout=javax.swing.ViewportLayout
			//	System.out.println("textarea.getParent().getParent(): " + textarea.getParent().getParent()); 			//javax.swing.JScrollPane[,10,0,190x80,layout=javax.swing.ScrollPaneLayout
			//	System.out.println("textarea.getParent().getParent().getParent(): " + textarea.getParent().getParent().getParent()); //javax.swing.JPanel[,0,40,210x80,layout=javax.swing.BoxLayout
			//	System.out.println("textarea.getParent().getParent().getParent().getParent(): " + textarea.getParent().getParent().getParent().getParent());  //javax.swing.JPanel[,0,0,210x130,layout=javax.swing.BoxLayout
			//	System.out.println("textarea.getParent().getParent().getParent().getParent().getParent(): " + textarea.getParent().getParent().getParent().getParent().getParent());  //latte.transparentpanel[,530,486,210x130,hidden
			//  fifth container is transparentpanel
				System.out.println("Event source component: " + e.getSource());
				textarea.getParent().getParent().getParent().getParent().getParent().setVisible(true);
			//	textarea.getParent().getParent().setVisible(true);
			}
		});		//end of mouse listener
		
		
		definitionTextAreaScrollpane.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				definitionTextAreaScrollpane.getParent().getParent().getParent().setVisible(true);
				definitionTextAreaScrollpane.getParent().getParent().getParent().getParent().setVisible(true);
				definitionTextAreaScrollpane.getParent().getParent().getParent().dispatchEvent(e);
	//			System.out.println("Mouse entered jscrollpane --> 4 levels, event dispatched");
	//			System.out.println(dc.getParent().getParent().getParent().getParent().getClass());
	//			System.out.println("Mouse entered jscrollpane --> 3 levels, event dispatched");
	//			System.out.println(dc.getParent().getParent().getParent().getClass());
			}
			public void mouseExited(MouseEvent e) {
				JScrollPane dc = (JScrollPane)e.getSource();
	///			System.out.println("Mouse exited JScrollPane, event dispatched");
				dc.getParent().getParent().getParent().setVisible(true);
				dc.getParent().getParent().getParent().getParent().setVisible(true);
			}
		}); 	//end of mouse listener
		
		domains.setText(lemmaob.domains);
		prop6.setText(" | " + lemmaob.sumo); 
		prop7.setText(lemmaob.nookid);
		prop8.setText(String.valueOf(lemmaob.synsetid));
		
		setIcon(lemmaob.lang);
		//setAlpha(.8f);
		revalidate();
		//URL url = getClass().getResource("/images/gr.gif");
		//ImageIcon icon = new ImageIcon(url);

	}
	
	public void setImg(BufferedImage img){
		 this.img = img;
	 }
	
	/*
	public void paintComponent(Graphics g) {
		
        super.paintComponent(g);       
        // Draw Text
        
        BufferedImage img = null;
		try {
			img = ImageIO.read(new File("images/v0_master.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
       // Graphics2D g2 = img.createGraphics();
        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
        g2d.setColor(Color.blue);
        g2d.fillRect(5, 5, 20, 40);
        //g2d.drawImage(img, xform, obs)
       // g2d.drawImage(img, -30, -45, null);
        //g2d.translate(100.0, 100.0); 
        //Graphics.drawImage(img, x, y, null)
        //g.drawString("This is my custom Panel!",10,20);
    }  
	
*/
		

	public void setIcon (String lemmalang) throws IOException {
		String lang = lemmalang;
		BufferedImage gla = ImageIO.read(new File("images/country_icons/Vatican-City.png"));
		BufferedImage gfr = ImageIO.read(new File("images/country_icons/France2.png"));  //"images/COUNTRY_ICONS/France_Waving_Flag.PNG"
		BufferedImage gru = ImageIO.read(new File("images/country_icons/Russia2.png"));   //images/language_logos/RUS_27px.gif
		BufferedImage gen = ImageIO.read(new File("images/country_icons/United-Kingdom2.png")); //images/COUNTRY_ICONS/uk20.PNG
		BufferedImage ggr = ImageIO.read(new File("images/country_icons/Greece2.png"));  //images/COUNTRY_ICONS/gr22.PNG	"images/COUNTRY_ICONS/GreekFlag-waving.PNG"
		BufferedImage gno = ImageIO.read(new File("images/country_icons/Norway2.png"));
		//gen.getScaledInstance(30, 21, gen.SCALE_SMOOTH);
		//gen = Scalr.resize(gen, 40);
		if (lang.equals("la")) {
			myicon.getImage().flush();
			myicon.setImage(gla);
			langsign.setIcon(myicon);
		}
		else if (lang.equals("fr")) {
			myicon.getImage().flush();
			myicon.setImage(gfr);
			langsign.setIcon(myicon);
			//langsign.setIcon(new ImageIcon(gfr));
		}
		else if (lang.equals("gr")) {
			myicon.getImage().flush();
			myicon.setImage(ggr);
			langsign.setIcon(myicon);
			//langsign.setIcon(new ImageIcon(gfr));
		}
		else if (lang.equals("en")) {
			myicon.getImage().flush();
			myicon.setImage(gen);
			langsign.setIcon(myicon);
			//langsign.setIcon(new ImageIcon(gfr));
		}
		else if (lang.equals("ru")) {
			myicon.getImage().flush();
			myicon.setImage(gru);
			langsign.setIcon(myicon);
			//langsign.setIcon(new ImageIcon(gfr));
		}
		else if (lang.equals("ns")) {
			myicon.getImage().flush();
			myicon.setImage(gno);
			langsign.setIcon(myicon);
			//langsign.setIcon(new ImageIcon(gfr));
		}
		
		revalidate();
	}
	public transparentpanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public transparentpanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public transparentpanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
	
	class Adapter extends MouseAdapter {
	/*	final int tx = getLocationOnScreen().x;	//always 0
		final int ty = getLocationOnScreen().y;
		final int tw = getWidth();
		final int th = getHeight();
		*/
		public void mouseEntered(MouseEvent e) {
			System.out.println("Mouse Entered Event is in TP MouseAdapter");
	//		System.out.println("source is: " + e.getSource()); // --> latte.transparentpanel[,713,486,210x130...
		}
		public void mouseExited(MouseEvent e) {
			Object source = e.getSource();
			if (source instanceof JXPanel) {
				JXPanel thispanel = (JXPanel)e.getSource();
				thispanel.setVisible(false);
  			System.out.println("source is: " + e.getSource());
			}
			else if (source instanceof JTextArea) {
  	//			System.out.println("source is: " + e.getSource());
				JTextArea thistextarea = (JTextArea)e.getSource();
				if (e.getYOnScreen() > thistextarea.getParent().getParent().getParent().getLocationOnScreen().y+thistextarea.getParent().getParent().getParent().getHeight())
				{
	//				System.out.println("JXPanel.getY() = " + thistextarea.getParent().getParent().getParent().getLocationOnScreen().y);
				//	thistextarea.getParent().getParent().getParent().setVisible(false);
				}
	//			System.out.println("JXPanel.getY()+Height = " + (thistextarea.getParent().getParent().getParent().getLocationOnScreen().y+thistextarea.getParent().getParent().getParent().getHeight()));
	//			System.out.println("e.getYOnScreen()() = " + e.getYOnScreen());
			}
			//mouseEventListener.hidetp();
			//System.out.println("height: " + thispanel.getHeight());
			//System.out.println("width: " + thispanel.getWidth());
		};
	}
	
    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            System.out.println(me);
        }
        
        @Override
        public void mouseEntered(MouseEvent me) {
            System.out.println(me);
        }
    }
	
public void addAdapter(){
	addMouseListener(new Adapter());
}

}
