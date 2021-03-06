//Christian Barth
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;


public class Board extends JFrame
{
	private board gameboard;	//just a jpanel with a wood background	
	private JMenuBar menubar;	//for all things in the menu
	private JMenu start,quit;	
	private FlowLayout layout;	
	private JPanel[] rows;			//to help with formatting, playerpieces are placed into rows
	private playerpiece[][] buttons;	//an array of all the pieces
	private ButtonHandler handler;		//deals with turns + movement
	private JMenuItem Player2, Player3, Player4, Player6;	//how many players selection
	private MenuHandler menuHandler;
	private JMenuItem QuitDropDown;	
	private JTextField announceturn;	//text at the bottom of the gameboard
	private JMenuItem instructions;

	private int turn;				//handles how turns function
	private int numOfPlayers;			//handles the max number of turns
	public Board()
	{
		super("Chinese Checkers");
		gameboard=new board();
		menubar= new JMenuBar();
		start= new JMenu("Start");
		quit=new JMenu("Quit");

		turn=1;				//first player's turn

		Player2= new JMenuItem("2 Players");
		Player3=new JMenuItem("3 Players");
		Player4=new JMenuItem("4 Players");
		Player6= new JMenuItem("6 Players");
		instructions=new JMenuItem("How to Play");
		QuitDropDown=new JMenuItem("Quit Game");

		announceturn= new JTextField("Green",20);	//the first player is green
		Font font=new Font(announceturn.getText(),Font.BOLD,12);
		announceturn.setFont(font);

		gameboard.setVisible(true);
		layout= new FlowLayout(FlowLayout.CENTER,3,3);
		buttons= new playerpiece[18][13];
		rows= new JPanel[18];
		handler=new ButtonHandler();
		menuHandler= new MenuHandler();

		for(int x=0;x<18;x++)
		{
			rows[x]=new Row();
			rows[x].setLayout(layout);
		}

		{
			//creating all emptyspaces in the appropriate format
			//have to do it explicity since the designed of the board varies
			//and the shape of it is reflexive
			buttons[0][7]=new playerpiece(0,0,7);
			buttons[1][6]=new playerpiece(0,1,6);
			buttons[1][7]=new playerpiece(0,1,7);
			buttons[2][6]=new playerpiece(0,2,6);
			buttons[2][7]=new playerpiece(0,2,7);
			buttons[2][8]=new playerpiece(0,2,8);
			buttons[3][5]=new playerpiece(0,3,5);
			buttons[3][6]=new playerpiece(0,3,6);
			buttons[3][7]=new playerpiece(0,3,7);
			buttons[3][8]=new playerpiece(0,3,8);
			for(int x=0;x<13;x++)
			{
				buttons[4][x]=new playerpiece(0,4,x);
			}
			for(int x=0;x<12;x++)
			{
				buttons[5][x]=new playerpiece(0,5,x);
			}
			for(int x=1;x<12;x++)
			{
				buttons[6][x]=new playerpiece(0,6,x);
			}
			for(int x=1;x<11;x++)
			{
				buttons[7][x]=new playerpiece(0,7,x);
			}
			for(int x=2;x<11;x++)		//middle
			{
				buttons[8][x]=new playerpiece(0,8,x);
			}
			for(int x=1;x<11;x++)
			{
				buttons[9][x]=new playerpiece(0,9,x);
			}
			for(int x=1;x<12;x++)
			{
				buttons[10][x]=new playerpiece(0,10,x);
			}
			for(int x=0;x<12;x++)
			{
				buttons[11][x]=new playerpiece(0,11,x);
			}
			for(int x=0;x<13;x++)
			{
				buttons[12][x]=new playerpiece(0,12,x);
			}
			buttons[13][5]=new playerpiece(0,13,5);
			buttons[13][6]=new playerpiece(0,13,6);
			buttons[13][7]=new playerpiece(0,13,7);
			buttons[13][8]=new playerpiece(0,13,8);
			buttons[14][6]=new playerpiece(0,14,6);
			buttons[14][7]=new playerpiece(0,14,7);
			buttons[14][8]=new playerpiece(0,14,8);
			buttons[15][6]=new playerpiece(0,15,6);
			buttons[15][7]=new playerpiece(0,15,7);
			buttons[16][7]=new playerpiece(0,16,7);
		}

		menubar.add(start);
		menubar.add(quit);
		this.setJMenuBar(menubar);
		this.add(gameboard);
		start.add(Player2);
		start.add(Player3);
		start.add(Player4);
		start.add(Player6);
		start.add(instructions);
		quit.add(QuitDropDown);
		//action listener is menuselection and places player
		//pieces on the board
		Player2.addActionListener(menuHandler);
		Player3.addActionListener(menuHandler);
		Player4.addActionListener(menuHandler);
		Player6.addActionListener(menuHandler);
		instructions.addActionListener(menuHandler);
		QuitDropDown.addActionListener(menuHandler);

		gameboard.setLayout(new GridLayout(20,0,0,0));

		for(int x=0;x<18;x++)
		{
			gameboard.add(rows[x]);
		}
		int counter=0;

		for( int x=0;x<17;x++)		//adding pieces to the appropriate rows
		{
			for (int q=0;q<13;q++)
			{
				if(buttons[x][q]!=null)
				{
					rows[x].add(buttons[x][q]);
				}
			}
		}
		//gets rid of whitespace for the turn announcements
		announceturn.setEditable(false);
		announceturn.setBorder(BorderFactory.createEmptyBorder());
		announceturn.setOpaque(false);
		rows[17].add(announceturn);

	}//end of constructor

	private int gamewincheck()//used later on to see if a player has won
	{
		//if the fifth player's pieces are in the desired position
		//then return the fifth player
		int winner=5;
		if((buttons[4][0].playernum()==winner) && (buttons[4][1].playernum()==winner) &&
		(buttons[4][2].playernum() ==winner)&& (buttons[4][3].playernum()==winner))
		{
			if((buttons[5][0].playernum()==winner) && (buttons[5][1].playernum()==winner) &&
			(buttons[5][2].playernum()==winner))
			{
				if((buttons[6][1].playernum()==winner) &&(buttons[6][2].playernum()==winner))
				{
					if (buttons[7][1].playernum()==winner)
					{
						announceturn.setText("Pink Wins");
						return winner;
					}
				}

			}	


		}
		//the same for the second player
		//and so on
		winner=2;
		if((buttons[12][0].playernum()==winner) && (buttons[12][1].playernum()==winner) &&
		(buttons[12][2].playernum() ==winner)&& (buttons[12][3].playernum()==winner))
		{
			if((buttons[11][0].playernum()==winner) && (buttons[11][1].playernum()==winner) &&
			(buttons[11][2].playernum()==winner))
			{
				if((buttons[10][1].playernum()==winner) &&(buttons[10][2].playernum()==winner))
				{
					if (buttons[9][1].playernum()==winner)
					{
						announceturn.setText("Blue Wins");
						return winner;
					}
				}

			}	


		}

		winner=1;
		if((buttons[12][12].playernum()==winner) && (buttons[12][11].playernum()==winner) &&
		(buttons[12][10].playernum() ==winner)&& (buttons[12][9].playernum()==winner))
		{
			if((buttons[11][11].playernum()==winner) && (buttons[11][10].playernum()==winner) &&
			(buttons[11][9].playernum()==winner))
			{
				if((buttons[10][11].playernum()==winner) &&(buttons[10][10].playernum()==winner))
				{
					if (buttons[9][10].playernum()==winner)
					{
						announceturn.setText("Green Wins");
						return winner;
					}
				}

			}	


		}


		
		winner=4;
		if((buttons[4][12].playernum()==winner) && (buttons[4][11].playernum()==winner) &&
		(buttons[4][10].playernum() ==winner)&& (buttons[4][9].playernum()==winner))
		{
			if((buttons[5][11].playernum()==winner) && (buttons[5][10].playernum()==winner) &&
			(buttons[5][9].playernum()==winner))
			{
				if((buttons[6][11].playernum()==winner) &&(buttons[6][10].playernum()==winner))
				{
					if (buttons[7][10].playernum()==winner)
					{
						announceturn.setText("Red Wins");
						return winner;
					}
				}

			}	


		}
		winner=6;
		if((buttons[3][8].playernum()==winner) && (buttons[3][7].playernum()==winner) &&
		(buttons[3][6].playernum() ==winner)&& (buttons[3][5].playernum()==winner))
		{
			if((buttons[2][8].playernum()==winner) && (buttons[2][7].playernum()==winner) &&
			(buttons[2][6].playernum()==winner))
			{
				if((buttons[1][7].playernum()==winner) &&(buttons[1][6].playernum()==winner))
				{
					if (buttons[0][7].playernum()==winner)
					{
						announceturn.setText("Yellow Wins");
						return winner;
					}
				}

			}	


		}


		winner=3;
		if((buttons[13][8].playernum()==winner) && (buttons[13][7].playernum()==winner) &&
		(buttons[13][6].playernum() ==winner)&& (buttons[13][5].playernum()==winner))
		{
			if((buttons[14][8].playernum()==winner) && (buttons[14][7].playernum()==winner) &&
			(buttons[14][6].playernum()==winner))
			{
				if((buttons[15][7].playernum()==winner) &&(buttons[15][6].playernum()==winner))
				{
					if (buttons[16][7].playernum()==winner)
					{
						announceturn.setText("Orange Wins");
						return winner;
					}
				}

			}	


		}

		return 0;
		//0 indicates no winner
	}

	//used when a player wins to remove all pieces
	//getting ready for the next game
	private void clearScreen()
	{

	for( int x=0;x<17;x++)
			{
				for (int q=0;q<13;q++)
				{
					if(buttons[x][q]!=null)
					{
						buttons[x][q].setPlayer(0);
						buttons[x][q].removeActionListener(handler);
					}
				}
			}

	}




	//used as the gameboard
	//simply a jpanel with a sick wood background
	private class board extends JPanel
	{
		private Image background;

		public void paintComponent(Graphics g)
		{
			try{
			background=ImageIO.read(getClass().getResource("players/background.jpeg"));
				//ImageIO.read(new File("players/background.jpeg"));
			}catch(Exception ex){}

			super.paintComponent(g);
			g.drawImage(background.getScaledInstance(Board.this.getWidth(), Board.this.getHeight(),1),0,0,this);
		}
	}
	//all menu items are handled here
	private class MenuHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==QuitDropDown)		//if quit is selected
			{
				Board.this.setVisible(false);
				Board.this.dispose();	
			
			}
			if(event.getSource()==instructions)
			{
				Instructions instruct= new Instructions();
				instruct.setSize(400,200);
				instruct.setVisible(true);

			}
			//if player 2 selected, make 2 players
			if(event.getSource()==Player2)
			{
				for(int i=0;i<4;i++)
				{
				buttons[4][i].setPlayer(1);
				buttons[4][12-i].setPlayer(2);
				}
				for(int i=0;i<3;i++)
				{
				buttons[5][i].setPlayer(1);
				buttons[5][11-i].setPlayer(2);
				}
				for(int i=1;i<3;i++)
				{
				buttons[6][i].setPlayer(1);
				buttons[6][12-i].setPlayer(2);
				}
				buttons[7][1].setPlayer(1);
				buttons[7][10].setPlayer(2);

				numOfPlayers=2;

			}
			//make 3 players and so on and so forth
			else if(event.getSource()==Player3)
			{
				for(int i=0;i<4;i++)
				{
				buttons[4][i].setPlayer(1);
				buttons[4][12-i].setPlayer(2);
				buttons[3][8-i].setPlayer(3);
				}
				for(int i=0;i<3;i++)
				{
				buttons[5][i].setPlayer(1);
				buttons[5][11-i].setPlayer(2);
				buttons[2][8-i].setPlayer(3);
				}
				for(int i=1;i<3;i++)
				{
				buttons[6][i].setPlayer(1);
				buttons[6][12-i].setPlayer(2);
				buttons[1][8-i].setPlayer(3);
				}
				buttons[7][1].setPlayer(1);
				buttons[7][10].setPlayer(2);
				buttons[0][7].setPlayer(3);

				
				numOfPlayers=3;
			}
			else if(event.getSource()==Player4)
			{
					
				for(int i=0;i<4;i++)
				{
				buttons[4][i].setPlayer(1);
				buttons[4][12-i].setPlayer(2);
				buttons[12][12-i].setPlayer(5);
				buttons[12][i].setPlayer(4);
				}
				for(int i=0;i<3;i++)
				{
				buttons[5][i].setPlayer(1);
				buttons[5][11-i].setPlayer(2);
				buttons[11][11-i].setPlayer(5);
				buttons[11][i].setPlayer(4);
				}
				for(int i=1;i<3;i++)
				{
				buttons[6][i].setPlayer(1);
				buttons[6][12-i].setPlayer(2);
				buttons[10][12-i].setPlayer(5);
				buttons[10][i].setPlayer(4);
				}
				buttons[7][1].setPlayer(1);
				buttons[7][10].setPlayer(2);
				buttons[9][10].setPlayer(5);
				buttons[9][1].setPlayer(4);

				
				numOfPlayers=4;
			}
			else if(event.getSource()==Player6)
			{
				for(int i=0;i<4;i++)
				{
				buttons[4][i].setPlayer(1);
				buttons[4][12-i].setPlayer(2);
				buttons[12][12-i].setPlayer(5);
				buttons[12][i].setPlayer(4);
				buttons[3][8-i].setPlayer(3);
				buttons[13][8-i].setPlayer(6);
				}
				for(int i=0;i<3;i++)
				{
				buttons[5][i].setPlayer(1);
				buttons[5][11-i].setPlayer(2);
				buttons[11][11-i].setPlayer(5);
				buttons[11][i].setPlayer(4);
				buttons[2][8-i].setPlayer(3);
				buttons[14][8-i].setPlayer(6);
				}
				for(int i=1;i<3;i++)
				{
				buttons[6][i].setPlayer(1);
				buttons[6][12-i].setPlayer(2);
				buttons[10][12-i].setPlayer(5);
				buttons[10][i].setPlayer(4);
				buttons[1][8-i].setPlayer(3);
				buttons[15][8-i].setPlayer(6);
				}
				buttons[7][1].setPlayer(1);
				buttons[7][10].setPlayer(2);
				buttons[9][10].setPlayer(5);
				buttons[9][1].setPlayer(4);
				buttons[0][7].setPlayer(3);
				buttons[16][7].setPlayer(6);


				numOfPlayers=6;
				
			}

			for( int x=0;x<17;x++)			//for all rows
			{
				for (int q=0;q<13;q++)		//for all columns
				{
					if(buttons[x][q]!=null)	//if it isn't an empty space
					{
						if(buttons[x][q].playernum()!=0)	//and it is a playable piece
						{
							buttons[x][q].addActionListener(handler);
						}
						
					}
				}
			}

		}//end of action performed


	}//end of class

	//this deals with turns
	private class ButtonHandler implements ActionListener
	{
		playerpiece temp;
		int flag=0;	//flag exists to make sure only one ButtonHandler runs at a time
		playerpiece[] possiblemoves;
		gameHandler empty;
		int row;
		int column;
		int playernum;
		playerpiece[] diagnolmoves;

		int diagnolcount=0;

		public void actionPerformed(ActionEvent event)
		{

			temp=((playerpiece)event.getSource());

			if(temp.playernum()!=turn)				//if it is not your turn, leave this function
			{
				return;
			}

			//if this function is launched more than once, return
			if(flag==2)
				return;	
			flag=2;

				
			empty=new gameHandler();

			row=temp.getRow();

			column=temp.getColumn();
			playernum=temp.playernum();	
			possiblemoves=new playerpiece[6];	//stores all possible near by moves
			diagnolmoves=new playerpiece[20];	//stores all possible diagnol moves

								//assigns possible moves to be made
								//to an array, making sure to not include
								//moves that will not adhere to the rules
			if(column-1<0)
				possiblemoves[0]=null;
			else
				possiblemoves[0]=buttons[row][column-1];
			if(column+1>12)
				possiblemoves[1]=null;
			else
				possiblemoves[1]=buttons[row][column+1];
			if(row-1<0)
				possiblemoves[2]=null;
			else
				possiblemoves[2]=buttons[row-1][column];
			if(row+1>16)
				possiblemoves[3]=null;
			else
				possiblemoves[3]=buttons[row+1][column];
								//since the rows of the board do not fully line up (a line
								//containing an even number of things will not align with
								//one containing odd) diagnol moves have to be made explicit
			
			
		if(row%2==0)
		{
			
			if(row+1>16 || column-1<0)
				possiblemoves[4]=null;
			else
			{
				if(row==12)//fixes a bug caused from leaving the upper triangle to the body of the board
				{
					if(column+1==13)
					{
						possiblemoves[4]=null;
					}
					else
					{
						possiblemoves[4]=buttons[row+1][column+1];
					}
				}
				else
				{
					possiblemoves[4]=buttons[row+1][column-1];
				}

			}
			if(row-1<0 || column-1<0)
				possiblemoves[5]=null;
			else
			{
				if(row==4)//the same bug is present with the lower and upper triangle
				{
					if(column+1==13)
					{
						possiblemoves[5]=null;
					}
					else
					{
						possiblemoves[5]=buttons[row-1][column+1];
					}
				}
				else
				{
					possiblemoves[5]=buttons[row-1][column-1];
				}
			}
		}
		else
		{
			if(row+1>16 || column+1>12)
				possiblemoves[4]=null;
			else
			{
				if(row==3)//here it is again
				{
					possiblemoves[4]=buttons[row+1][column-1];
				}
				else
				{
					possiblemoves[4]=buttons[row+1][column+1];
				}
			}
			if(row-1<0 || column+1>12)
				possiblemoves[5]=null;
			else
			{
				if(row==13)//the final fix
				{
					possiblemoves[5]=buttons[row-1][column-1];

				}
				else
				{
					possiblemoves[5]=buttons[row-1][column+1];
				}
			}
		}

				//adding an additional actionlistener to those empty nearby spaces
				//if a nearby space is filled by a player piece, send it to the positionbehind
				//function to determine if it can be jumped				
				for(int x=0;x<possiblemoves.length;x++)
				{
					if(possiblemoves[x]==null)
					{
						continue;
					}
					if(possiblemoves[x].playernum()==0)
					{
						possiblemoves[x].addActionListener(empty);
					}
					else
					{
						
						positionBehind(row,column,possiblemoves[x]);
					}

				}

			
		}//end of actionperformed	




		private class gameHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				//swap the empty piece for the player piece
				playerpiece emptyTemp=((playerpiece)event.getSource());
				buttons[emptyTemp.getRow()][emptyTemp.getColumn()].setPlayer(playernum);
				buttons[row][column].setPlayer(0);

				//removing all action listeners
				for(int x=0;x<possiblemoves.length;x++)
				{
				if(possiblemoves[x]==null)
				{
					continue;
				}
				possiblemoves[x].removeActionListener(empty);
				}
				//removing all diagnol action listeners
				for(int x=0;x<diagnolmoves.length;x++)
				{
					if(diagnolmoves[x]==null)
					{
						continue;
					}
				diagnolmoves[x].removeActionListener(empty);
				}

				buttons[emptyTemp.getRow()][emptyTemp.getColumn()].addActionListener(handler);
				buttons[row][column].removeActionListener(handler);
				flag=0;			//flag set to 0 so that another piece can be selected again
				diagnolcount=0;

				turn++;				//implementing turns
				if(numOfPlayers==4 &&turn==3)
				{
				turn=turn+1;
				}
				if(turn>numOfPlayers &&numOfPlayers!=4)		//implementing turns
				{						//for 4 players
					turn=1;
				}
				if(numOfPlayers==4 &&turn>5)
				{
					turn=1;
				}
				changeText(turn);		//this function changed the display text
				if(0<gamewincheck())				//checks for win at the end of every move
				{
					clearScreen();
				}
			}

		}//end of gamehandler

		//changes the text of the Jtextfield below board
		//depending on whose turn it is
		private void changeText(int turn)
		{
			if(turn==1)
			{
				announceturn.setText("Green");
			}

			if(turn==2)
			{
				announceturn.setText("Blue");
			}
			else if(turn==3)
			{
				announceturn.setText("Orange");
			}
			else if(turn==4)
			{
				announceturn.setText("Red");
			}
			else if(turn==5)
			{
				announceturn.setText("Pink");
			}
			else if(turn==6)
			{
				announceturn.setText("Yellow");
			}

		}


		//a function for jumping
		private void positionBehind(int row, int column, playerpiece button)
		{
		int temprow=button.getRow();				//stores the button's location
		int tempcolumn=button.getColumn();

		int behindrow;						//stores the row behind the button
		int behindcolumn;
		int differencerow;
		int differencecolumn;
		int everyother=0;

		differencerow=row-temprow;		//find the difference between the selected piece
		differencecolumn=column-tempcolumn;	//and the piece trying to be jumped


		behindrow=temprow;
		behindcolumn=tempcolumn;
		//this is the bug that appears from going from the player3 and player6 positions
		//fixed by adjusting the row
		if(temprow==3)	
		{
			if(differencerow==1 &&differencecolumn==0)
			{
				differencecolumn=1;

			}
		}
		else if(temprow==4)
		{
			if(differencerow==-1 && differencecolumn==0)
			{
				
				differencecolumn=-1;
			}
		}
		if(temprow==13)
		{
			if(differencerow==-1 && differencecolumn==0)
			{
				differencecolumn=1;
			}
		}
		else if(temprow==12)
		{
			if(differencerow==1 && differencecolumn==0)
			{
				differencecolumn=-1;
			}
		


		}
		//this runs until there isn't a piece to jump
		while(true)
		{
			

			behindrow=behindrow-differencerow;			//get first piece behind piece in question
			//everyOther is used to adjust from even number rows to odd number
			differencecolumn=everyOther(differencerow,differencecolumn,everyother,behindrow);
			behindcolumn=behindcolumn-differencecolumn;
			//fix the bug again
			if(behindrow==4)
			{
				if(differencecolumn==-1 &&differencerow==-1)
				{
					behindcolumn=behindcolumn-1;
				}
				if(differencecolumn==0 && differencerow==-1)
				{
					behindcolumn=behindcolumn-1;
				}	
			}
			if(behindrow==3)
			{	
				if(differencecolumn==1 && differencerow==1)
				{
				behindcolumn=behindcolumn+1;
				}
				if(differencecolumn==0&&differencerow==1)
				{
				behindcolumn=behindcolumn+1;
				}

			}
			if(behindrow==12)
			{
				if(differencecolumn==0 && differencerow==1)
				{
					behindcolumn=behindcolumn-1;
				}
				else if(differencecolumn==-1 && differencerow==1)
				{
					behindcolumn=behindcolumn-1;
				}	

			}
			if(behindrow==13)
			{
				if(differencecolumn==1 && differencerow==-1)
				{
					behindcolumn=behindcolumn+1;

				}
				if(differencecolumn==0 && differencerow==-1)
				{
					behindcolumn=behindcolumn+1;
				}


			}
			if(behindrow<0 || behindcolumn<0 || behindrow>16 ||behindcolumn>12)
				break;
			if(buttons[behindrow][behindcolumn]==null)
				break;
			//if there is an empty piece, add an actionlistener
			if(buttons[behindrow][behindcolumn].playernum()==0)
			{
				buttons[behindrow][behindcolumn].addActionListener(empty);
				diagnolmoves[diagnolcount]=buttons[behindrow][behindcolumn];
				diagnolcount++;
			}
			else
				break;

			everyother++;
			behindrow=behindrow-differencerow;
			differencecolumn=everyOther(differencerow,differencecolumn,everyother,behindrow);
			behindcolumn=behindcolumn-differencecolumn;
			
			if(behindcolumn>12 || behindcolumn<0)
				break;
			if(behindrow>16 || behindrow<0)
				break;
			if(buttons[behindrow][behindcolumn]==null || 
			buttons[behindrow][behindcolumn].playernum()==0)
				break;
			everyother=everyother-1;

		}	//end of while

		}//end of positionBehind

		private int everyOther(int diffrow, int diffcolumn, int everyother, int behindrow)
		{
			//runs for every other row and adjust spacing if appropriate
			if(everyother%2==0)
			{
				if(row%2!=0)
				{	
					if(diffrow==1 && diffcolumn==0)
					{
						return 1;
					}
					else if(diffrow==-1 && diffcolumn==0)
					{
						return 1;
					}
					else if(diffrow==-1 && diffcolumn==-1)
					{
						return 0;
					}
					else if(diffrow==1 && diffcolumn==-1)
					{
						return 0;
					}
				}
				else
				{
					if(diffrow==-1 && diffcolumn==0)
					{
						return -1;
					}
					else if(diffrow==-1 && diffcolumn==1)
					{
						return 0;
					}
					else if(diffrow==1 && diffcolumn==1)
					{
						return 0;
					}
					else if(diffrow==1 && diffcolumn==0)
					{
						return -1;
					}
				}
			}
			else
			{
				if(row%2!=0)
				{
					if(diffrow==1 && diffcolumn==1)
					{
						return 0;
					}
					else if(diffrow==-1 && diffcolumn==1)
					{
						return 0;
					}
					else if(diffrow==-1 && diffcolumn==0)
					{
						return -1;
					}
					else if(diffrow==1 && diffcolumn==0)
					{
						return -1;
					}
				}
				else
				{	
					if(diffrow==-1 && diffcolumn==-1)
					{
						return 0;
					}
					else if(diffrow==-1 && diffcolumn==0)
					{
						return 1;
					}
					else if(diffrow==1 && diffcolumn==0)
					{
						return -1;
					}
					else if(diffrow==1 && diffcolumn==-1)
					{
						return 0;
					}
				}
				



			}

		return diffcolumn;
		}//end of everyother function

	}	//end of outermost actionlistener class

}//end of board class
