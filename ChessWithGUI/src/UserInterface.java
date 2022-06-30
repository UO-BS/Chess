import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class to get user input from console
 * @author UO-BS
 */
public class UserInterface{

    public enum GameStates{
        MENUS, STARTMOVE, ENDMOVE, GAMEOVER, CUSTOMPLACEMENT;
    }

    private JFrame frame;
    private JPanel menuPanel;
    private JPanel gameBoard;

    private JLabel[] labelComponents; //Index: 0 GameType, 1 SecondPlayerName (Black), 2 FirstPlayerName (White), 3 4 5 GameOutputMessages, 6 MenuPrompts 
    private Game currentGame;
    private Player[] playerList;
    private String gameType;
    private JButton[][] gamePositions;  //The game board made of buttons
    private GameStates currentState;    //Current state of the game. Changes the behavior of certain buttons
    private Move tempMove;              //Temporarily holds the current move that the user is trying to make
    private PieceType customPlaceType;
    private Player customPlacingPlayer;

    public UserInterface() {
        
        createFrame();

        labelComponents = new JLabel[7];
        menuPanel = createMenuPanel();
        JPanel gamePanel = createGamePanel();
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JLabel prompt = new JLabel("");
        
        addComponentGridBag(mainPanel, prompt, 1, 0, 1, 1,1,0.5);
        labelComponents[6]=prompt;
        addComponentGridBag(mainPanel, menuPanel, 1, 1, 2, 1,1,1);
        addComponentGridBag(mainPanel, gamePanel, 0, 0, 3, 1,1,1);
        frame.add(mainPanel);
        frame.revalidate();

        currentState = GameStates.MENUS;
        displayPrompt("What type of game do you want to play?");
    }

    /**
     * Generates a chess JFrame
     */
    private void createFrame(){
        frame = new JFrame("Chess");
        frame.setSize(1200,750);
        frame.setMinimumSize(new Dimension(1200,750));
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);       
    }

    /**
     * Creates a chess Game JPanel arranged using the <code>GridBagLayout</code>
     * 
     * <p>
     * The Game JPanel of a chess game contains:
     * <p>
     * 6 <code>JLabel</code> which list: (1 Label) the gametype, (2 Labels) the names of the players and (3 Labels) the output to the user from the game
     * <p>
     * <code>JPanel</code> which holds the game board
     * 
     * @return JPanel holding chess game components
     */
    private JPanel createGamePanel(){
        JPanel gamePanel = new JPanel(new GridBagLayout());

        JLabel gameTitle = new JLabel("GameType: None");
        addComponentGridBag(gamePanel, gameTitle, 1, 0, 1, 1,1,1);
        labelComponents[0]=gameTitle;
        JLabel player2Name = new JLabel("Player 2");
        addComponentGridBag(gamePanel, player2Name, 0, 1, 1, 1,1,1);
        labelComponents[1]=player2Name;
        JLabel player1Name = new JLabel("Player 1");
        addComponentGridBag(gamePanel, player1Name, 0, 2, 1, 1,1,1);
        labelComponents[2]=player1Name;

        JLabel gameOutput1 = new JLabel("");
        addComponentGridBag(gamePanel, gameOutput1, 1, 4, 1, 1,1,0.25);
        labelComponents[3]=gameOutput1;
        JLabel gameOutput2 = new JLabel("");
        addComponentGridBag(gamePanel, gameOutput2, 1, 5, 1, 1,1,0.25);
        labelComponents[4]=gameOutput2;
        JLabel gameOutput3 = new JLabel("");
        addComponentGridBag(gamePanel, gameOutput3, 1, 6, 1, 1,1,0.25);
        labelComponents[5]=gameOutput3;

        
        gameBoard = new JPanel();
        addComponentGridBag(gamePanel, gameBoard, 1, 1, 2, 1,1,1);

        

        return gamePanel;
    }

    /**
     * Creates a chess Menu JPanel arranged using the <code>CardLayout</code>
     * 
     * <p>
     * The Menu JPanel of a chess game contains:
     * <p>
     * Main Menu <code>JPanel</code> which holds <code>JButtons</code> for the user to choose the Game Type
     * <p>
     * Name Menu <code>JPanel</code> which holds <code>JTextFields</code> and <code>JButton</code> for the user to enter the player names
     * <p>
     * Pawn Promotion Menu <code>JPanel</code> which holds <code>JButton</code> for the user to choose what piece type 
     * they want to promote to in the event of a pawn promotion move
     * <p>
     * Custom Board Menu <code>JPanel</code> which holds <code>JTextFields</code> and <code>JButton</code> for the user to enter custom board height and width
     * <p>
     * Custom Piece Menu <code>JPanel</code> which holds <code>JButton</code> for the user to choose what piece type to place on the board during custom game setup
     * 
     * @return JPanel holding chess menu components
     */
    private JPanel createMenuPanel(){
        JPanel menuPanel = new JPanel();
        CardLayout cards = new CardLayout();
        menuPanel.setLayout(cards);
        
        //Main starting menu, The user decides between standard, custom and chess960 mode
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));

        JButton standardButton = new JButton("Standard");
        standardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    gameType="Standard";
                    cards.show(menuPanel,"NameMenu");
                    displayPrompt("What are the names of the players?");
                    labelComponents[0].setText("GameType: Standard");
                }
            }
        });
        mainMenuPanel.add(standardButton);
        
        JButton customButton = new JButton("Custom");
        customButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    gameType="Custom";
                    cards.show(menuPanel,"NameMenu");
                    displayPrompt("What are the names of the players?");
                    labelComponents[0].setText("GameType: Custom");
                }
            }
        });
        mainMenuPanel.add(customButton);

        JButton chess960Button = new JButton("Chess960");
        chess960Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    gameType="Chess960";
                    cards.show(menuPanel,"NameMenu");
                    displayPrompt("What are the names of the players?");
                    labelComponents[0].setText("GameType: Chess960");
                }
            }
        });
        mainMenuPanel.add(chess960Button);

        //Panel for the user to enter usernames
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        JLabel namePrompt1 = new JLabel("Enter player 1's name:");
        usernamePanel.add(namePrompt1);
        JTextField nameBox1 = new JTextField("Player1", 15);
        usernamePanel.add(nameBox1);
        JLabel namePrompt2 = new JLabel("Enter player 2's name:");
        usernamePanel.add(namePrompt2);
        JTextField nameBox2 = new JTextField("Player2", 15);
        usernamePanel.add(nameBox2);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    playerList = new Player[2];
                    playerList[0] = new Player(nameBox1.getText(),1);
                    playerList[1] = new Player(nameBox2.getText(),-1);
                    labelComponents[2].setText(playerList[0].getName());;
                    labelComponents[1].setText(playerList[1].getName());;
                    if (!gameType.equals("Custom")) {
                        currentGame = new Game(playerList, gameType);
                        initializeGameBoard(currentGame.getBoard());
                        drawBoard(currentGame.getBoard());
                        currentState=GameStates.STARTMOVE;
                        displayText(currentGame.getCurrentTurn()+"'s turn");
                    } else {
                        cards.show(menuPanel,"CustomBoardMenu");
                        displayPrompt("Enter a height and width of the custom board (Between 4-14 inclusive)");
                    }
                }
            }
        });
        usernamePanel.add(submitButton);

        //Menu for the user to pick what piece they want to promote their pawn to during PawnPromotion
        JPanel pawnPromotionPanel = new JPanel();
        pawnPromotionPanel.setLayout(new BoxLayout(pawnPromotionPanel, BoxLayout.Y_AXIS));
        
        JButton queenButton = new JButton("Queen");
        queenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    currentGame.promotePawn(PieceType.QUEEN);
                    drawBoard(currentGame.getBoard());
                    switchTurns();
                }
            }});
        pawnPromotionPanel.add(queenButton);
        
        JButton rookButton = new JButton("Rook");
        rookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    currentGame.promotePawn(PieceType.ROOK);
                    drawBoard(currentGame.getBoard());
                    switchTurns();
                }
            }});
        pawnPromotionPanel.add(rookButton);
        
        JButton bishopButton = new JButton("Bishop");
        bishopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    currentGame.promotePawn(PieceType.BISHOP);
                    drawBoard(currentGame.getBoard());
                    switchTurns();
                }
            }});
        pawnPromotionPanel.add(bishopButton);
        
        JButton knightButton = new JButton("Knight");
        knightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    currentGame.promotePawn(PieceType.KNIGHT);
                    drawBoard(currentGame.getBoard());
                    switchTurns();
                }
            }});
        pawnPromotionPanel.add(knightButton);

        //Menu for managing custom mode board creation
        JPanel customBoardPanel = new JPanel();
        customBoardPanel.setLayout(new BoxLayout(customBoardPanel, BoxLayout.Y_AXIS));
        JLabel heightPrompt = new JLabel("Enter the height of the board:");
        customBoardPanel.add(heightPrompt);
        JTextField heightBox = new JTextField("8", 15);
        customBoardPanel.add(heightBox);
        JLabel widthPrompt = new JLabel("Enter the width of the board:");
        customBoardPanel.add(widthPrompt);
        JTextField widthBox = new JTextField("8", 15);
        customBoardPanel.add(widthBox);
        JButton submitBoardButton = new JButton("Submit");
        submitBoardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    if (isValidPositiveIntInput(heightBox.getText(), 14, 4) && isValidPositiveIntInput(widthBox.getText(), 14, 4)) {
                        currentGame = new Game(playerList, gameType);
                        currentGame.setBoard(new Board(Integer.parseInt(heightBox.getText()),Integer.parseInt(widthBox.getText())));
                        initializeGameBoard(currentGame.getBoard());
                        drawBoard(currentGame.getBoard());
                        customPlacingPlayer=playerList[0];
                        cards.show(menuPanel,"CustomMenu");
                        displayPrompt("Placing "+customPlacingPlayer+"'s pieces");
                    }
                }
            }
        });
        customBoardPanel.add(submitBoardButton);

        //Menu for managing custom mode piece placement
        JPanel customModeOptionsPanel = new JPanel();
        customModeOptionsPanel.setLayout(new BoxLayout(customModeOptionsPanel, BoxLayout.Y_AXIS));
        JButton kingPlaceButton = new JButton("King");
        kingPlaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    boolean hasKing =false;
                    for (Piece piece:customPlacingPlayer.getPieceList()){
                        if (piece.getPieceType()==PieceType.KING) {
                            hasKing=true;
                        }
                    }
                    if (!hasKing) {
                        customPlaceType=PieceType.KING;
                        currentState=GameStates.CUSTOMPLACEMENT;
                    }
                }
            }});
        customModeOptionsPanel.add(kingPlaceButton);
        
        JButton queenPlaceButton = new JButton("Queen");
        queenPlaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    customPlaceType=PieceType.QUEEN;
                    currentState=GameStates.CUSTOMPLACEMENT;
                }
            }});
        customModeOptionsPanel.add(queenPlaceButton);
        
        JButton rookPlaceButton = new JButton("Rook");
        rookPlaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    customPlaceType=PieceType.ROOK;
                    currentState=GameStates.CUSTOMPLACEMENT;
                }
            }});
        customModeOptionsPanel.add(rookPlaceButton);
        
        JButton bishopPlaceButton = new JButton("Bishop");
        bishopPlaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    customPlaceType=PieceType.BISHOP;
                    currentState=GameStates.CUSTOMPLACEMENT;
                }
            }});
        customModeOptionsPanel.add(bishopPlaceButton);
        
        JButton knightPlaceButton = new JButton("Knight");
        knightPlaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    customPlaceType=PieceType.KNIGHT;
                    currentState=GameStates.CUSTOMPLACEMENT;
                }
            }});
        customModeOptionsPanel.add(knightPlaceButton);

        JButton pawnPlaceButton = new JButton("Pawn");
        pawnPlaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    customPlaceType=PieceType.PAWN;
                    currentState=GameStates.CUSTOMPLACEMENT;
                }
            }});
        customModeOptionsPanel.add(pawnPlaceButton);

        JButton switchButton = new JButton("Switch Player");
        switchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    int nextIndex = -1;
                    for (int i=0;i<playerList.length;i++) {
                        if (customPlacingPlayer==playerList[i]) {
                            nextIndex = i+1;
                        }
                    }
                    if (nextIndex>=playerList.length) customPlacingPlayer=playerList[0] ;
                    else customPlacingPlayer=playerList[nextIndex] ;
                    displayPrompt("Placing "+customPlacingPlayer+"'s pieces");
                }
            }});
        customModeOptionsPanel.add(switchButton);

        JButton doneButton = new JButton("Start Game");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.MENUS) {
                    boolean missingKing =false;
                    for (Player player:playerList) {
                        boolean hasKing=false;
                        for (Piece piece:player.getPieceList()){
                            if (piece.getPieceType()==PieceType.KING) {
                                hasKing=true;
                            }
                        }
                        if (!hasKing) {
                            missingKing=true;
                        }
                    }
                    if (!missingKing) {
                        for (int i=0;i<playerList.length;i++) {
                            for (Piece piece:playerList[i].getPieceList()){
                                if (piece.getPieceType()==PieceType.KING) {
                                    currentGame.getKingList()[i]=piece;
                                }
                            }
                        }
                        currentState=GameStates.STARTMOVE;
                        displayText(currentGame.getCurrentTurn()+"'s turn");
                    }
                }
            }});
        customModeOptionsPanel.add(doneButton);
                
        //Adding all the different menu panels to the card layout
        menuPanel.add(mainMenuPanel,"MainMenu");
        menuPanel.add(usernamePanel,"NameMenu");
        menuPanel.add(pawnPromotionPanel,"PawnMenu");
        menuPanel.add(customBoardPanel,"CustomBoardMenu");
        menuPanel.add(customModeOptionsPanel,"CustomMenu");
        cards.show(menuPanel,"MainMenu");

        return menuPanel;
    }

    /**
     * Creates a grid of <code>JButton</code> on the GameBoard <code>JPanel</code> corresponding to a given <code>Board</code> object
     * <p>
     * Contains the local class <code>BoardButtonListener</code> which is used as an <code>ActionListener</code> for the board JButtons
     * 
     * @param board A <code>Board</code> object that will initialized
     */ 
    private void initializeGameBoard(Board board){
        class BoardButtonListener implements ActionListener{
            private int thisX;
            private int thisY;
            public BoardButtonListener(int x, int y){
                thisX = x;
                thisY = y;
            }
            public void actionPerformed(ActionEvent e) {
                if (currentState==GameStates.STARTMOVE) {
                    if (board.getPosition(thisY, thisX).getCurrentPiece()==null) {
                        displayText("There is no piece at that position");
                    } else if (!board.getPosition(thisY, thisX).getCurrentPiece().getOwner().equals(currentGame.getCurrentTurn())){
                        displayText("You cannot move that piece");
                    }else {
                        tempMove = new Move(board.getPosition(thisY, thisX), null);
                        currentState=GameStates.ENDMOVE;
                    }
                
                } else if (currentState==GameStates.ENDMOVE) {
                    tempMove.setEndPosition(board.getPosition(thisY, thisX));
                    ArrayList<Move> validMoveList = currentGame.allValidMoves(currentGame.getCurrentTurn(),Game.allPossibleMoves(board,currentGame.getCurrentTurn()));
                    int moveIndex = currentGame.isValidMove(tempMove,validMoveList);
                    if (moveIndex==-1) { //Invalid move
                        currentState=GameStates.STARTMOVE;
                        displayText("Invalid Move");
                    } else {
                        currentGame.movePiece(validMoveList.get(moveIndex));
                        if (validMoveList.get(moveIndex).getMoveType()==MoveType.PAWNPROMOTION) {
                            doPawnPromotion();
                        }
                        drawBoard(board);
                        if (currentState==GameStates.ENDMOVE) { //The state could be MENUS if there was a pawn promotion
                            switchTurns();
                        }
                    }
                
                } else if (currentState==GameStates.CUSTOMPLACEMENT) {
                    currentGame.placeCustomPiece(customPlacingPlayer, customPlaceType, board.getPosition(thisY, thisX));
                    drawBoard(board);
                    currentState=GameStates.MENUS;
                }
            }
        }

        gameBoard.removeAll();
        gameBoard.setLayout(new GridLayout(board.getRows(),board.getColumns()));
        gamePositions = new JButton[board.getRows()][board.getColumns()];

        for (int y=board.getRows()-1;y>=0;y--){
            for (int x=0;x<board.getColumns();x++) {
                JButton boardButton = new JButton();
                boardButton.addActionListener(new BoardButtonListener(x,y));
                gameBoard.add(boardButton);
                gamePositions[y][x] = boardButton;
            }
        }

    }

    /**
     * 
     * Draws the corresponding textures on the initialized GameBoard's JButtons based on a Board object
     * 
     * @param board A <code>Board</code> object that will drawn
     */ 
    private void drawBoard(Board board){
        BufferedImage img;
        Piece currentPiece;
        for (int y=0;y<board.getRows();y++){
            for (int x=0;x<board.getColumns();x++) {
                currentPiece = board.getPosition(y, x).getCurrentPiece();
                try {
                    if      (currentPiece==null) img = ImageIO.read(UserInterface.class.getResource("pieceTexture/Blank_space.png"));
                    else    img = ImageIO.read(UserInterface.class.getResource(currentPiece.getImageFilepath()));
                    gamePositions[y][x].setIcon(new ImageIcon(img));
                } catch (IOException e) {
                    System.out.println("image file missing");
                }
            }
        }
    }

    /**
     * Helper Method to simplify adding components to a <code>GridBagLayout</code>
     * 
     * @param targetPanel   The JPanel that the components are being added to
     * @param component     The Component object being added to the JPanel
     * @param gridX         {@link GridBagConstraints#gridx}
     * @param gridY         {@link GridBagConstraints#gridy}
     * @param gridHeight    {@link GridBagConstraints#gridheight}
     * @param gridWidth     {@link GridBagConstraints#gridwidth}
     * @param weightX       {@link GridBagConstraints#weightx}
     * @param weightY       {@link GridBagConstraints#weighty}
     */ 
    private void addComponentGridBag(JPanel targetPanel, Component component ,int gridX, int gridY, int gridHeight, int gridWidth,double weightX, double weightY){
        GridBagConstraints cBagConstraints = new GridBagConstraints();
        cBagConstraints.gridx=gridX;
        cBagConstraints.gridy=gridY;
        cBagConstraints.gridheight=gridHeight;
        cBagConstraints.gridwidth=gridWidth;
        cBagConstraints.weightx=weightX;
        cBagConstraints.weighty=weightY;
        cBagConstraints.fill = GridBagConstraints.BOTH;
        targetPanel.add(component,cBagConstraints);
    }

    /**
     * Changes the current game turn, determines check/checkmate/stalemate and ensures the game is not over before allowing the next player to move
     * <p>
     * If the game is over: calls the displayGameOver() method 
     */ 
    private void switchTurns(){
        boolean checked=false;
        currentGame.nextTurn();
        ArrayList<Move> moveOptions = currentGame.allValidMoves(currentGame.getCurrentTurn(), Game.allPossibleMoves(currentGame.getBoard(), currentGame.getCurrentTurn()));
        
        displayText(currentGame.getCurrentTurn().getName() +"'s turn");
        if (currentGame.inCheck(currentGame.getCurrentTurn())) {
            displayText(currentGame.getCurrentTurn().getName() +" is in check");
            checked=true;
        }

        if (moveOptions.size()==0) {
            if (checked) {
                displayText(currentGame.getCurrentTurn().getName()+" has been checkmated!");
                currentGame.getCurrentTurn().setPlayerState(1);
            } else {
                displayText(currentGame.getCurrentTurn().getName()+" has been stalemated!");
                currentGame.getCurrentTurn().setPlayerState(2);
            }
        } 

        if (currentGame.checkWin()) {
            currentState=GameStates.GAMEOVER;
            displayGameOver();
        } else currentState=GameStates.STARTMOVE;
        
    }

    /**
     * Prepares the User Interface for a new game
     */
    private void displayGameOver(){
        labelComponents[0].setText("GameType: None"); ;
        displayText(currentGame.getWinner()+" is the winner!");
        currentState = GameStates.MENUS;
        ((CardLayout)menuPanel.getLayout()).show(menuPanel, "MainMenu");
        displayPrompt("What mode would you like to play for the next game?");
    }

    /**
     * Displays output text to the user. 
     * 
     * @param displayText The text to be displayed to the user
     */
    public void displayText(String displayText){
        (labelComponents[3]).setText(labelComponents[4].getText());
        (labelComponents[4]).setText(labelComponents[5].getText());
        (labelComponents[5]).setText(displayText);
    }

    /**
     * Displays a text prompt to the user. 
     * 
     * @param displayText The text prompt to be displayed to the user
     */
    public void displayPrompt(String displayText){
        labelComponents[6].setText(displayText);
    }

    /**
     * Displays the move history to the user.
     * Currently it simply prints it to console.
     * 
     * @param moveHistory The move history that is being displayed.
     */
    public static void displayMoveHistory(LinkedList<Move> moveHistory){
        ListIterator<Move> moveHistoryIterator = moveHistory.listIterator();
        System.out.println("Move History:");
        while (moveHistoryIterator.hasNext()) {
            System.out.print(moveHistoryIterator.next()+" | ");
            if (moveHistoryIterator.hasNext()) {
                System.out.print(moveHistoryIterator.next());
            }
            System.out.println("");
        }
    }

    /**
     * Changes currentState to the Menus and makes the user choose what piece type to promote the pawn
     */
    public void doPawnPromotion(){
        currentState = GameStates.MENUS;
        ((CardLayout)menuPanel.getLayout()).show(menuPanel, "PawnMenu");
        displayPrompt("Promote pawn to what piece?");
    }

    /**
     * Checks that a given String is a valid integer between 2 int values
     * 
     * @param userInput The String being validated
     * @param maxValue The maximum int that the userInput be
     * @param minValue The minimum int that the userInput be
     */
    private boolean isValidPositiveIntInput(String userInput, int maxValue, int minValue){
        for (char c : userInput.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        int value = Integer.parseInt(userInput);
        if (value<minValue || value>maxValue) {
            return false;
        }
        return true;
    }

}