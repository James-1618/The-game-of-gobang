package test;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;
import javax.swing.*; 
public class wuziq  {
    public static void main(String args[]){ 
   	bosx b=new bosx(34,22,21);
   	if(b.time==0)
   	{
   		b.begin_white();
   		b.begin_black();
   		b.undo();
   		b.begin();
   		b.again();
   	}
 }
}
class bosx extends JFrame
{
	public int count;
	public int time;
	public int time1;
	public int win;
	public int n1;
	public int n2;
	public int[][][] a;
	public int width;
	public double page_x;
	public double page_y;
	Stack<Integer> staf = new Stack<Integer>();//堆栈  
//创建一个JLayeredPane用于分层的。
	JLayeredPane layeredPane;
//创建一个Panel和一个Label用于存放图片，作为背景。
	JPanel jp;
	JLabel jl;
	JLabel title;
	JLabel []label_w=new JLabel[1000];
	JLabel []label_b=new JLabel[1000]; 
	ImageIcon image;
	JButton button1,button2,button3,button4,button5;
	public bosx(int w,double x,double y)            //界面设置
	{
		count=0;
		time=0;
		time1=0;
		win=0;
		n1=-1;
		n2=-1;
		a=new int[19][19][2];
		//panel_w=new JPanel[1000];
		//panel_b=new JPanel[1000];
		width=w;
		page_x=x;
		page_y=y;
		title=new JLabel("双人五子棋大战");
		title.setFont(new Font("华文行楷",1, 80));
		title.setForeground(Color.black);
		title.setBounds(100,0,900,150);
		layeredPane=new JLayeredPane();
		image=new ImageIcon("./img/bg.png");
	    jp=new JPanel();
   	    jp.setBounds(0,150,655,655);
		jl=new JLabel(image);
		jp.add(jl);
		layeredPane.add(title,JLayeredPane.DEFAULT_LAYER);
	    layeredPane.add(jp,JLayeredPane.DEFAULT_LAYER);
	    button1=new JButton("开始");
		button2=new JButton("悔棋");
	    button3=new JButton("黑棋先手");
		button4=new JButton("白棋先手");
		button5=new JButton("复盘");
		button1.setBounds(700,600,80,30);
		button2.setBounds(700,700,80,30);
		button3.setBounds(700,400,120,30);
		button4.setBounds(700,500,120,30);
		button5.setBounds(700,300,120,30);
		layeredPane.add(button1,JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(button2,JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(button3,JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(button4,JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(button5,JLayeredPane.DEFAULT_LAYER);
		this.setLayeredPane(layeredPane);
		this.setSize(900,900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(0,0);
		this.setVisible(true);   
	}
	public void build_stack(int x,int y,int[] coord,int[] num)        //保存落子路径堆栈
	{
		int x1;
		int y1;
		x1=(int) Math.floor((x+(width/2)-page_x)/width);
		y1=(int) Math.floor((y+(width/2)-page_y)/width);
		num[0]=x1;
		num[1]=y1;
		x1=x1*width+(int)page_x-(width/2);
		y1=y1*width+(int)page_y-(width/2)+153;
		coord[0]=x1;
		coord[1]=y1;
	}
	public void back_xy(int[] coord)                 //悔棋时弹出两步棋坐标位置
	{
		int x,y,numb;
		numb=staf.pop();
		x=staf.pop();
		y=staf.pop();
		a[x][y][0]=0;
		x=x*width+(int)page_x-(width/2);
		y=y*width+(int)page_y-(width/2)+153;
		coord[0]=x;
		coord[1]=y;
		coord[2]=numb;                                           //黑白棋标签号
		count=count-1;
	}
	public void add_()                                       //落子
	{
		jl.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)
			{
					
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO 自动生成的方法存根
					
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				int x,y;
				int []coord=new int[2];
				int []num=new int[2];
				x=e.getX();
				y=e.getY();
				build_stack(x,y,coord,num);
				if(a[num[0]][num[1]][0]==0)                          //设置黑白棋交替落子
				{	
					staf.push(num[1]);
					staf.push(num[0]);
					a[num[0]][num[1]][0]=1;
					count++;
					if(count%2==0)
					{
						n1++;
						staf.push(n1+1);
						a[num[0]][num[1]][1]=2;
						ImageIcon img1=new ImageIcon("./img/white.png");
						label_w[n1]=new JLabel(img1);
						label_w[n1].setBounds(coord[0],coord[1],34,34);
						layeredPane.add(label_w[n1],JLayeredPane.MODAL_LAYER);
					}
					else if(count%2==1)
					{
						n2++;
						staf.push(-(n2+1));
						a[num[0]][num[1]][1]=3;
						ImageIcon img2=new ImageIcon("./img/black.png");
						label_b[n2]=new JLabel(img2);
						label_b[n2].setBounds(coord[0],coord[1],34,34);
						layeredPane.add(label_b[n2],JLayeredPane.MODAL_LAYER);
					}
					win=check_result();
					if(win==1)
					{
						jp.removeAll();
						image=new ImageIcon("./img/bg.png");
						jl=new JLabel(image);
						jp.add(jl);
						layeredPane.add(jp,JLayeredPane.PALETTE_LAYER);
						layeredPane.remove(button2);
						layeredPane.remove(button1);
						layeredPane.remove(button3);
						layeredPane.remove(button4);
						again();
					}
				}
					
			}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO 自动生成的方法存根
					
			}
		});
	}
	public void begin()                                      //开始
	{
		button1.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)
			{
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
					
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
					
			}

			@Override
			public void mousePressed(MouseEvent e) {
				time++;                         
				add_();
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
					
			}
		});
	}
	public int begin_white()                           //白棋先手
	{
			button4.addMouseListener(new MouseListener()
			{
				public void mouseClicked(MouseEvent e)
				{
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO 自动生成的方法存根
					
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO 自动生成的方法存根
					
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					if(time==0)
					{
						count=1;
						begin_black();
						begin();
					}
					
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO 自动生成的方法存根
					
				}
			});
		return 0;
	}
	public int begin_black()                          //黑棋先手
	{
		button3.addMouseListener(new MouseListener()
			{
				public void mouseClicked(MouseEvent e)
				{

				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO 自动生成的方法存根
						
				}
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO 自动生成的方法存根
						
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					if(time==0)
					{
						count=0;
						begin_white();
						begin();
					}
					
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO 自动生成的方法存根
						
				}
			});
		return 0;
	}
	public void undo()                            //悔棋
	{
		button2.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)
			{
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
					
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
					
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(count>-1)
				{
					int[] coord=new int[3];
					back_xy(coord);
					if(coord[2]>0)
					{
						layeredPane.remove(label_w[coord[2]-1]);
						ImageIcon img=new ImageIcon("./img/undo.png");
						JLabel label_1=new JLabel(img);
						label_1.setBounds(coord[0],coord[1],34,34);
						layeredPane.add(label_1,JLayeredPane.PALETTE_LAYER);	
						
					}
					else
					{
						layeredPane.remove(label_b[-coord[2]-1]);
						ImageIcon img=new ImageIcon("./img/undo.png");
						JLabel label_1=new JLabel(img);
						label_1.setBounds(coord[0],coord[1],34,34);
						layeredPane.add(label_1,JLayeredPane.PALETTE_LAYER);	
					}
						
					back_xy(coord);
					if(coord[2]>0)
					{
						layeredPane.remove(label_w[coord[2]-1]);
						ImageIcon img=new ImageIcon("./img/undo.png");
						JLabel label_1=new JLabel(img);
						label_1.setBounds(coord[0],coord[1],34,34);
						layeredPane.add(label_1,JLayeredPane.PALETTE_LAYER);	
					}
					else
					{
						layeredPane.remove(label_b[-coord[2]-1]);
						ImageIcon img=new ImageIcon("./img/undo.png");
						JLabel label_1=new JLabel(img);
						label_1.setBounds(coord[0],coord[1],34,34);
						layeredPane.add(label_1,JLayeredPane.PALETTE_LAYER);	
					}
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
					
			}
		});
	}
	public int check()                                                                //检查盘面情况，是否有获胜方
	{
		int i,j,k,number,over=0;
		for(i=0;i<19;i++)
		{
			for(j=0;j<19;j++)
			{
				number=0;
				if(a[i][j][0]==1)
				{
					for(k=-2;k<3;k++)
					{
						if((i+k)<0||(j+k)<0||(i+k)>18||(j+k)>18)
							break;
						if(k==0)
							continue;
						if(a[i+k][j+k][0]==1&&(a[i][j][1]==a[i+k][j+k][1]))
						{
							number++;
						}
						else
							break;
					}
					if(number!=4)
					{
						number=0;
						for(k=-2;k<3;k++)
						{
							if((i+k)<0||(j-k)<0||(i+k)>18||(j-k)>18)
								break;
							if(k==0)
								continue;
							if(a[i+k][j-k][0]==1&&(a[i][j][1]==a[i+k][j-k][1]))
							{
								number++;
							}
							else
								break;
						}
					}
					if(number!=4)
					{
						number=0;
						for(k=-2;k<3;k++)
						{
							if((i+k)<0||(i+k)>18)
								break;
							if(k==0)
								continue;
							if(a[i+k][j][0]==1&&(a[i][j][1]==a[i+k][j][1]))
							{
								number++;
							}
							else
								break;
						}
					}
					if(number!=4)
					{
						number=0;
						for(k=-2;k<3;k++)
						{
							if((j+k)<0||(j+k)>18)
								break;
							if(k==0)
								continue;
							if(a[i][j+k][0]==1&&(a[i][j][1]==a[i][j+k][1]))
							{
								number++;
							}
							else
								break;
						}
					}
					if(number==4)
					{
						if(a[i][j][1]==2)
						{
							over=2;
						}
						else if(a[i][j][1]==3)
						{
							over=3;
						}
					}
				}
				if(over>0)
				{
					return over;
				}
					
			}
		}
		return 0;
	}
	public int check_result()                                               //检查获胜方是黑棋还是白棋
	{
		if(check()==2)
		{
			JOptionPane.showMessageDialog(null, "白棋获胜");
			return 1;
		}
		else if(check()==3)
		{
			JOptionPane.showMessageDialog(null, "黑棋获胜");
			return 1;
		}
		else
		{
			return 0;
		}
	}
	public void again()                                                    //复盘
	{
		button5.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)
			{
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO 自动生成的方法存根
				int i,j,k;
				for(i=0;i<19;i++)
				{
					for(j=0;j<19;j++)
					{
						for(k=0;k<2;k++)
						{
							a[i][j][k]=0;
						}
					}
				}
				if(count>0)
				{
					for(i=count;i>-1;i--)
					{
						staf.pop();
					}
				}
				layeredPane.removeAll();
				count=0;
				time=0;
				time1=0;
				win=0;
				n1=-1;
				n2=-1;
				//panel_w=new JPanel[1000];
				//panel_b=new JPanel[1000];
				title=new JLabel("双人五子棋大战");
				title.setFont(new Font("华文行楷",1, 80));
				title.setForeground(Color.black);
				title.setBounds(100,0,900,150);
				layeredPane=new JLayeredPane();
				image=new ImageIcon("./img/bg.png");
			    jp=new JPanel();
		   	    jp.setBounds(0,150,655,655);
				jl=new JLabel(image);
				jp.add(jl);
				layeredPane.add(title,JLayeredPane.DEFAULT_LAYER);
			    layeredPane.add(jp,JLayeredPane.DEFAULT_LAYER);
			    button1=new JButton("开始");
				button2=new JButton("悔棋");
			    button3=new JButton("黑棋先手");
				button4=new JButton("白棋先手");
				button5=new JButton("复盘");
				button1.setBounds(700,600,80,30);
				button2.setBounds(700,700,80,30);
				button3.setBounds(700,400,120,30);
				button4.setBounds(700,500,120,30);
				button5.setBounds(700,300,120,30);
				layeredPane.add(button1,JLayeredPane.DEFAULT_LAYER);
				layeredPane.add(button2,JLayeredPane.DEFAULT_LAYER);
				layeredPane.add(button3,JLayeredPane.DEFAULT_LAYER);
				layeredPane.add(button4,JLayeredPane.DEFAULT_LAYER);
				layeredPane.add(button5,JLayeredPane.DEFAULT_LAYER);
				setLayeredPane(layeredPane);
				setSize(900,900);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setLocation(0,0);
				setVisible(true);   
				begin_white();
		   		begin_black();
		   		undo();
		   		begin();
		   		again();
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
		});
	}
}