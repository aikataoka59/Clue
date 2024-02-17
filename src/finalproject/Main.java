
package finalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.Random;


public class Main {
    static int mode =0;
    static int currentL = 4;
    static int currentN =4;
    static String privious_spot ="";
    static String current_spot = "D4";
    static char currentL_char = 'D';
    static boolean insideRoom = false;
    
    public static void main(String[] args) {
        createFile();
        menu();
    }
    
    public static void menu(){
        //This method doen't take any input and doen't return any output.
        //It is a main menu screen that can start the game and can show details about the game.
        System.out.println("\nWelcome to JoJo's Bizarre Game");
        System.out.println("\n1.Start Game");
        System.out.println("2.Check Rule");
        System.out.println("3.Leave Game");
        System.out.println("4.About Game");
        
        int order =0;
        while(order != 3){
            order = scanInt("\nType the number: ",4);
            switch(order){
            case 1:
                System.out.println("");
                game();
                menu();
                order=3;
                break;
            case 2:
                
                System.out.println("\nHere is the game rule:");
                System.out.println("");
                System.out.println("\nIn this game, you should solve a mystery of the murder. \nYou get information of 6 suspects, 9 weapons, 9 places. You have to make correct accusation by selecting ones from them. ");
                System.out.println("Before making accusation, you can make a guess and the game will give you a hint after that. You can   make a guess only when you are at  places where might the murder happened.");
                System.out.println("\nThe level of game: \nEasy - You can move anywhere but there is a limit to make a guess. You can make a guess only 15 times.\nNormal - You have to move on the map by throwing a dice.");
                System.out.println("\nThe secret passages: \nYou can find a secret passages between a place and a place if you meet a condition. You can get an useful information about the murder case.");
                break;
            case 3:
                System.out.println("\nThank you for playing!");
                break;
            case 4:
                System.out.println("\nThis game was made based on a board game called \"Clue\". The name of places, characters and weapons are from \"JoJo's Bizarre Adventure\".");
            default:
            }
        }
    }
    
    public static void game(){
        //This method doen't take any input and doen't return any output.
        //It lets the main features of this game move and switch the order depends on the user input.
        try{
            selectLevel();
            if(mode ==1){
                insideRoom = true;
            }else{
                insideRoom = false;
                currentL = 4;
                currentN =4;
                privious_spot ="";
                current_spot = "D4";
                currentL_char = 'D';
            }
            
            String solution [] = new String[3];
            solution = Solution();
            
            boolean gameover = false;
            int action =3;
            String current_room ="";
            String previous_room="";
            Random rand = new Random();
            int RandomNum1 = rand.nextInt((int) (8-1+1))+1;
            int RandomNum2 = rand.nextInt((int) (8-1+1))+1;
            if(RandomNum1 == RandomNum2){
                RandomNum2++;
            }
            String seacret1 = returnToString("room", RandomNum1);
            String seacret2 = returnToString("room", RandomNum2);
            int guess_count =0;
            boolean cancel = false;
            while(gameover == false){
                String select_room;
                String select_suspect;
                String select_weapon;
                if(action == 1){
                    int check1 = 1;
                    if(mode == 1){
                        System.out.println("\nDo you really want to make a guess?");
                        System.out.println("You have "+(15-guess_count)+" chances to make a guess");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        check1 = scanInt("\nType the number: ",2);
                        if(check1 == 2){
                            cancel = true;
                        }
                    }
                    if(check1 == 1){
                        guess_count++;
                        boolean correct_select = false;
                        String guess [] = new String[3];
                        while(correct_select == false){
                            select_room = current_room;
                            System.out.println("\nLet's make a guess!");

                            System.out.println("\nWho do you think did it?");
                            showSuspects();
                            int select_suspectNum = scanInt("\nType the number: ",6);
                            select_suspect = returnToString("suspect", select_suspectNum);
                            System.out.println("\nWhich weapons do you think the person used it?");
                            showWeapons();
                            int select_weaponNum = scanInt("\nType the number: ",9);
                            select_weapon = returnToString("weapon", select_weaponNum);
                            int check2 = checkselect(select_suspect, select_room,  select_weapon);
                            if(check2 == 1){
                                guess[0]  = select_suspect;
                                guess[1] = select_room;
                                guess[2] = select_weapon;
                                correct_select = true;
                            }
                        }
                        checkResult(solution,guess,1);
                    }
                    
                    action =3;
                }else if(action ==2){
                    System.out.println("\nDo you really want to make accusation?");
                    System.out.println("(After that, the game will be finished. )");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    int check = scanInt("\nType the number: ",2);
                    if(check == 2){
                        cancel = true;
                        action =3;
                    }else{
                        boolean correct_select = false;
                        String accusation [] = new String[3];
                        while(correct_select== false){
                            System.out.println("\nLet's make accusation!");
                            System.out.println("\nWho do you think did it?");
                            showSuspects();
                            int select_suspectNum = scanInt("\nType the number: ",6);
                            select_suspect = returnToString("suspect", select_suspectNum);
                            System.out.println("\nWhere do you think the person did it?");
                            showRooms();
                            int select_roomNum = scanInt("\nType the number: ",9);
                            select_room = returnToString("room", select_roomNum);
                            System.out.println("\nWhich weapons do you think the person used for it?");
                            showWeapons();
                            int select_weaponNum = scanInt("\nType the number: ",9);
                            select_weapon = returnToString("weapon", select_weaponNum);
                            int check2 = checkselect(select_suspect, select_room,  select_weapon);
                            if(check2 == 1){
                                correct_select = true;
                                accusation[0]  = select_suspect;
                                accusation[1] = select_room;
                                accusation[2] = select_weapon;
                            }
                        }
                        checkResult(solution,accusation,2);
                        gameover = true;
                    }
                    
                }else if(action == 3){
                    if(cancel == false){
                        previous_room = current_room;
                        if(mode == 1){
                            current_room =  move();
                        }else if(mode == 2 || mode ==3){
                            current_room = Newmove(current_room);
                        }

                        if(previous_room.equals("Morioh Grand Hotel")){
                            if(current_room.equals(seacret1) || current_room.equals(seacret2)){
                                System.out.println("\nYou found a secret passage in the way leaving from Morioh Grand Hotel.");
                                System.out.println("A girl living there asked you, \n\"Was what you dropped, this old notebook or \nthis piece of paper with a word written by blood?\"");
                                System.out.println("\"The notebook is very similar to mine\", you answered.");
                                System.out.println("\"Then, I would give both to such honest person like you.\" ");
                                System.out.println("The girl also whispered, \"But you must use them up before sunset or ...\" ");
                                System.out.println("After she vanished, you looked at the piece of paper.");
                                System.out.println("It said \""+solution[0] +"\", whose name is one of suspects.");
                            }
                        }
                    }else{
                        cancel = false;
                    }
                    
                    
                    System.out.println("\nWhat do you do at this place?");
                    if(insideRoom == true){
                        System.out.println("1. Make a guess");
                        System.out.println("2. Make accusation");
                        System.out.println("3. Leave here");
                        System.out.println("4. Leave game");
                        
                        action = scanInt("\nType the number: ",4);
                    }else if(insideRoom == false){
                        System.out.println("(You can make a guess only when you stay at a place)");
                        System.out.println("1. Make accusation");
                        System.out.println("2. Leave here");
                        System.out.println("3. Leave game");
                        action = scanInt("\nType the number: ",3);
                        action++;
                    }
 
                }else{
                    gameover = true;
                }
            }
        }catch(Exception e){
            System.out.println("\nAn error occured.");
            System.out.println(e);
        }

    }
    
    public static void selectLevel(){
        //This method doen't take any input and doen't return any output.
        //It ask the player to type in order to change to the game level. 
        System.out.println("\nChoose the game level: ");
        System.out.println("1. EASY");
        System.out.println("2. NORMAL");
        mode = scanInt("\nType the number: ",2);
    }
    
    
    public static String [] Solution() throws FileNotFoundException{
        //This method doen't take any input.
        //It makes solutions of the game for each game.
        //It returns the array including the solutions.
        String solution [] = new String[3];
        File suspectFile = new File("suspect.txt");
        File roomFile = new File("room.txt");
        File weaponFile = new File("weapon.txt");
        solution[0]  = pickSolution(suspectFile,6);
        solution[1] = pickSolution(roomFile,9);
        solution[2] = pickSolution(weaponFile,9);
        //System.out.print("\nSolution: ");
        //displayArray(solution);
         
        return solution;
    }
    
    public static String pickSolution(File file, int max) throws FileNotFoundException {
        //This method takes a file and the number of text in the file in input.
        //It picks a random number between 1 and the input number and get the solution from the file.
        //It returns the solution as string value.
        Scanner myReader = new Scanner(file);
        Random rand = new Random();
        int RandomNum = rand.nextInt((int) (max-1+1))+1;
        String solution = "";
        for(int  i = 0 ; i<RandomNum ; i++){
                solution = myReader.nextLine();
            }
        return solution;
    }
    

    
    public static void showRooms() throws FileNotFoundException{
        //This method doen't take any input and doen't return any output.
        //It displays all text in the room file.
        System.out.println("\nThe list of places where the murder might have happened.");
        File roomFile = new File("room.txt");
        Scanner roomReader = new Scanner(roomFile);
        for(int i =0; i<9; i++){
            String room = roomReader.nextLine();
            System.out.println((i+1)+" "+room);
        }
    }
    
    public static void showSuspects() throws FileNotFoundException{
        //This method doen't take any input and doen't return any output.
        //It displays all text in the suspect file.
        File suspectFile = new File("suspect.txt");
        Scanner suspectReader = new Scanner(suspectFile);
        for(int i =0; i<6; i++){
            String suspect = suspectReader.nextLine();
            System.out.println((i+1)+" "+suspect);
        }
        
    }
    
    public static void showWeapons() throws FileNotFoundException{
        //This method doen't take any input and doen't return any output.
        //It displays all text in the weapon file.
        File weaponFile = new File("weapon.txt");
        Scanner weaponReader = new Scanner(weaponFile);
        for(int i =0; i<9; i++){
            String weapon = weaponReader.nextLine();
            System.out.println((i+1)+" "+weapon);
        }
        
    }
    
    public static void displayMap(String current_room) throws FileNotFoundException{
        //This method takes the name of room where the player is currently in input.
        //It displays the map and the list of places,and shows the current spot where the player is.
        //It does't return any output.
        
        System.out.println("\n");
        System.out.println("    1  2  3  4   5  6  7  8  9 10 11 ");
        System.out.println("    ________ __ ________ __ ________ ");
        System.out.println("A  |        |__|        |__|        |");
        System.out.println("B  |  No.1  |__|  No.2  D__|  No.3  |");
        System.out.println("C  |_____ D |__| D _____|__| D _____|");
        System.out.println("D  |__|__|__|__|__|  |__|__|__|__|__|");
        System.out.println("E  |        |__D   D    |__|        |");
        System.out.println("F  |  No.4  D__|  No.5  |__D  No.6  |");
        System.out.println("G  |_____ D |__|___ D___D__| D _____|");
        System.out.println("H  |__|  |__|_ |__|__|  |__|__|  |__|");
        System.out.println("I  |   D    |__|      D |__|    D   |");
        System.out.println("J  |  No.7  |__|  No.8  D__|  No.9  |");
        System.out.println("K  |________|__|________|__|________|");
        System.out.println("                           (D = door)");
        if(insideRoom == true){
            System.out.println("\nYou are in "+current_room+" now.");
        }else{
            System.out.println("\nYou are at "+current_spot+" now.");
        }
        showRooms();
    }
    
    public static String move() throws FileNotFoundException{
        //This method doen't take any input.
        //For EASY mode, it shows the list of places and takes the user input to move from a place to a place.
        //It returns the name of room where the player stay currently.
            showRooms();
            int current_roomNum = scanInt("\nType the number of the place to go: ",9);
            File roomFile = new File("room.txt");
            Scanner myReader = new Scanner(roomFile);
            String current_room = "";
            for(int  i = 0 ; i<current_roomNum ; i++){
                    current_room = myReader.nextLine();
            }
            
            System.out.println("\nYou are in "+current_room+" now.");
            
            return current_room;
    }
    
        public static String Newmove(String current_room) throws FileNotFoundException{
            //This method takes the name of room where the player is currently in input.
            //For NORMAL mode, it let the player move on the map and enter the places through a door.
            //If the player try to move to a worng spot, it will spot it and ask the order again.
            //It returns the name of room where the player stay currently.
            

            displayMap(current_room);
            int diceNum = throwDice();
            while(diceNum>0){
                System.out.println("\nYou still can move "+diceNum+" times");
                if(insideRoom == true){
                    System.out.println("\nDo you leave this room?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    int order = scanInt("Type the number: ",2);
                    if(order == 1){
                        if(current_room.equals(returnToString("room",2))){
                            exit(2);
                        }else if(current_room.equals(returnToString("room",4))){
                            exit(4);
                        }else if(current_room.equals(returnToString("room",5))){
                            exit(5);
                        }else if(current_room.equals(returnToString("room",6))){
                            exit(6);
                        }else if(current_room.equals(returnToString("room",8))){
                            exit(8);
                        }
                                
                        insideRoom = false;
                        diceNum --;
                    }else{
                        diceNum =0;
                    }
                }
            
                if(insideRoom == false){
                    String order ="";
                    if("D3".equals(current_spot)){
                        order = door(1);
                    }else if("D5".equals(current_spot) || "B8".equals(current_spot)){
                        order =door(2);
                    }else if("D9".equals(current_spot)){
                        order =door(3);
                    }else if("F4".equals(current_spot) || "H3".equals(current_spot)){
                        order =door(4);            
                    }else if("D6".equals(current_spot) || "E4".equals(current_spot)|| "H6".equals(current_spot)|| "G8".equals(current_spot)){
                        order =door(5); 
                    }else if("F8".equals(current_spot) || "H9".equals(current_spot)){
                        order =door(6); 
                    }else if("H2".equals(current_spot)){
                        order =door(7); 
                    }else if("H7".equals(current_spot) || "J8".equals(current_spot)){
                        order =door(8);
                    }else if("H10".equals(current_spot)){
                        order =door(9);
                    }
                    
                    if("".equals(order)){
                        current_spot = currentL_char+""+currentN;
                        System.out.println("\nYou are at "+current_spot+" now.");
                    }else{
                        insideRoom = true;
                        current_room = order;
                        diceNum =0;
                    }      
                    if(insideRoom == false){
                        
                        boolean correct_move = false;
                        while(correct_move == false){
                            int check_L =currentL;
                            int check_N =currentN;
                            System.out.println("\nHow do you move?");
                            System.out.println("1. UP");
                            System.out.println("2. LEFT");
                            System.out.println("3. RIGHT");
                            System.out.println("4. DOWN");
                            int move = scanInt("Type the number: ",4);
                            switch(move){
                                case 1:
                                    check_L --;
                                    correct_move = checkMove(check_L,check_N);
                                    if(correct_move == true){
                                        currentL = currentL-1;
                                        currentL_char--;
                                    }
                                    
                                    break;
                                case 2:
                                    check_N --;
                                    correct_move = checkMove(check_L,check_N);
                                    if(correct_move == true){
                                        currentN = currentN-1;
                                    }
                                    break;
                                case 3: 
                                    check_N ++;
                                    correct_move = checkMove(check_L,check_N);
                                    if(correct_move == true){
                                        currentN = currentN+1;
                                    }
                                    
                                    break;
                                case 4: 
                                    check_L ++;
                                    correct_move = checkMove(check_L,check_N);
                                    if(correct_move == true){
                                        currentL = currentL+1;
                                        currentL_char++;
                                    }
                                    break;
                            }
                            
                            
                        } 
                    }

                    current_spot = currentL_char+""+currentN;
                    System.out.println("\nYou are at "+current_spot+" now.");
                    
                    
                }
                diceNum = diceNum-1;
               
            }
            return current_room;
        }
        
        public static int throwDice(){
            //This method doen't take any input.
            //It produces the random number of dice thrown.
            //It returns the number as output.
            Random rand = new Random();
            int RandomNum = rand.nextInt((int) (6-1+1))+1;
            System.out.println("\nYour throw the dice: "+RandomNum);
            return RandomNum;
        }
        
        public static void exit(int roomNum){
            //This method takes the number assigned each place.
            //It asks the user from which spot they want to exit from the place and move the player to the spot.
            //It does'n return any output.
            System.out.println("\nWhich door do you use to leave this room?");
            int order;
            if(roomNum == 2){
                System.out.println("1.D5");   
                System.out.println("2.B8");
                order = scanInt("Type the number: ",2);
                if(order ==1){
                    currentL_char ='D';
                    currentL = 4;
                    currentN = 5 ;
                }else{
                    currentL_char ='B';
                    currentL = 2;
                    currentN = 8 ;
                }
            }else if(roomNum == 4){
                System.out.println("1.F4");   
                System.out.println("2.H3");
                order = scanInt("Type the number: ",2);
                if(order ==1){
                    currentL_char ='F';
                    currentL = 6;
                    currentN = 4 ;
                }else{
                    currentL_char ='H';
                    currentL = 8;
                    currentN = 3 ;
                }            
            }else if(roomNum == 5){
                System.out.println("1.E4");   
                System.out.println("2.D6");
                System.out.println("3.H6");
                System.out.println("4.G8");
                order = scanInt("Type the number: ",4);
                if(order ==1){
                    currentL_char ='E';
                    currentL = 5;
                    currentN =4 ;
                }else if(order ==2){
                    currentL_char ='D';
                    currentL = 4;
                    currentN = 6 ;
                }else if(order ==3){
                    currentL_char ='H';
                    currentL = 8;
                    currentN =6 ;
                }else{ 
                    currentL_char ='G';
                    currentL = 7;
                    currentN = 8 ;
                }           
            }else if(roomNum == 6){
                System.out.println("1.F8");   
                System.out.println("2.H9");
                order = scanInt("Type the number: ",2);
                if(order ==1){
                    currentL_char ='F';
                    currentL = 6;
                    currentN = 8 ;
                }else{
                    currentL_char ='H';
                    currentL = 8;
                    currentN = 9 ;
                }            
            }else if(roomNum == 8){
                System.out.println("1.H7");   
                System.out.println("2.J8");
                order = scanInt("Type the number: ",2);
                if(order ==1){
                    currentL_char ='H';
                    currentL = 8;
                    currentN = 7 ;
                }else{
                    currentL_char ='J';
                    currentL = 10;
                    currentN = 8 ;
                }            
            }
            current_spot = currentL_char+""+currentN;
            
        }
       
        
        public static String door(int roomNum) throws FileNotFoundException{
            //This method takes the number assigned each place.
            //It asks the user if they want to enter the place.
            //It returns the name of room where the player stay currently.
            System.out.println("\nDo you enter Place No."+roomNum+"?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int order = scanInt("Type the number: ",2);
            String current_room = "";
            if(order == 1){
                current_room = returnToString("room",roomNum);
            }
                
            return current_room;
        }
        
        public static boolean checkMove(int check_L, int check_N){
            //This method takes two integer numbers representing the coordinate of the player.
            //It checks if the coordinate is in a wrong passage.
            //It returns the result of checking as output.
            boolean correct_move = false;
                if(check_L>=1 && check_L <= 11 && check_N>=1 && check_N <= 11){
                    if(check_L == 4 || check_L == 8){
                        correct_move = true;
                    }else{
                        if(check_N == 4 || check_N == 8){
                            correct_move = true;
                        }else{
                            System.out.println("You can't go that way");
                        }
                    }
                }else{
                    System.out.println("You can't go that way");
                }
            
            return correct_move;
        }
    
        public static String returnToString(String typeOfFile, int selectNum) throws FileNotFoundException{
            //This method takes the name of file and the number assinged to each text in the file.
            //It picks the text of the number from the file.
            //It returns the text as output.
            File suspectFile = new File("suspect.txt");
            File roomFile = new File("room.txt");
            File weaponFile = new File("weapon.txt");
            File File = suspectFile;
            switch(typeOfFile){
                case "suspect":
                    File = suspectFile;
                    break;
                case "room" :
                    File = roomFile;
                    break;
                case "weapon" : 
                    File = weaponFile;
                    break;
            }
            Scanner myReader = new Scanner(File);
            String result ="";
            for(int  i = 0 ; i<selectNum ; i++){
                result = myReader.nextLine();
            }
            return result;
        }

        public static int checkselect(String select_suspect,String select_room, String select_weapon){
            //This method takes the name of suspect, room and weapon in input.
            //It checks if those selects are really what the player slected.
            //It returns a number as the result of checking.
            System.out.println("\nYour guess  Suspect: "+select_suspect+"  Place: "+select_room+"  Weapon: "+select_weapon); 
            System.out.println("\nAre these correct?: ");
            System.out.println("1. Yes, display the result");
            System.out.println("2. No, select again");
            int check = scanInt("\nType the number: ",2);

            return check;
        }

        public static void checkResult(String [] solution,String [] guess, int mode){
            //This method takes two arrays representing a solution and a user guess, and also a number.
            //Depends on the input number, it shows the result of guessing or shows the result of accusation.
            //It doesn't return any output.
            boolean suspect = false;
            boolean room = false;
            boolean weapon = false;
            for(int i = 0;i<3;i++){
                if(solution[i].equals(guess[i])){
                    switch(i){
                        case 0:
                            suspect = true;
                            break;
                        case 1: 
                            room = true;
                            break;
                        case 2:
                            weapon = true;
                            break;
                    }
                }
            }   
            if(mode == 1){
                System.out.println("\nThe result of your guess: ");
                System.out.println("Place: "+room);
                if(room == true){
                    System.out.println("Suspect: "+suspect);
                    if(suspect == true){
                        System.out.println("Weapon: "+weapon);
                    }
                } 
            }else if(mode == 2){
                if(suspect == true && room == true && weapon == true){
                    System.out.println("\nYou Win!");
                }else{
                    System.out.println("\nYou Lose!");
                    System.out.print("Solution: " );
                    displayArray(solution);
                }
            }
        }

        public static void createFile(){
            //This method doen't take any input and doen't return any output.
            //It creates three files on the player's computer and write text to each file.
            //Those files are for the list of suspect, room and weapon.
            try{
                File suspectFile = new File("suspect.txt");
                if(suspectFile.createNewFile()){
                }else{
                }
                File roomFile = new File("room.txt");
                if(roomFile.createNewFile()){
                }else{
                }
                File weaponFile = new File("weapon.txt");
                if(weaponFile.createNewFile()){
                }else{
                }

                FileWriter suspectWriter = new FileWriter("suspect.txt");
                suspectWriter.write("Dio Brando");
                suspectWriter.write("\nKars");
                suspectWriter.write("\nYoshikage Kira");
                suspectWriter.write("\nVinegar Doppio");
                suspectWriter.write("\nEnrico Pucci");
                suspectWriter.write("\nFunny Valentine");
                suspectWriter.close();

                FileWriter roomWriter = new FileWriter("room.txt");
                roomWriter.write("The Chamber of The Two-Headed Dragon");
                roomWriter.write("\nRuins under Mouth of Truth");
                roomWriter.write("\nDIO's Mansion");
                roomWriter.write("\nGhost Girl's Alley");
                roomWriter.write("\nMorioh Grand Hotel");
                roomWriter.write("\nColosseum");
                roomWriter.write("\nCape Canaveral");
                roomWriter.write("\nPucci's Chapel");
                roomWriter.write("\nDevil's Palm");
                roomWriter.close();

                FileWriter sweaponWriter = new FileWriter("weapon.txt");
                sweaponWriter.write("The World");
                sweaponWriter.write("\nStone Mask");
                sweaponWriter.write("\nRed Stone of Aja");
                sweaponWriter.write("\nKiller Queen");
                sweaponWriter.write("\nKing Crimson");
                sweaponWriter.write("\nWhitesnake");
                sweaponWriter.write("\nD4C");
                sweaponWriter.write("\nStand Arrow");
                sweaponWriter.write("\nSteel Ball");
                sweaponWriter.close();

            }catch(Exception e){
                System.out.println("\nAn error occured.");
                System.out.println(e);
            }
        }

        public static int scanInt(String question, int max){
            //This method takes the sentence of a question and the max number of options.
            //It asks the question to the player and takes the integer user input.
            //It returns the user input as output.
            int myReader =0;
            boolean error = true;
            while(error == true){
                System.out.println(question);
                Scanner obj = new Scanner(System.in);
                try{
                    myReader = obj.nextInt();
                    if(myReader>0 && myReader <= max){
                        error = false;
                    }else{
                        System.out.println("That is not a valid input.");
                    }
                }catch(Exception e){
                    System.out.println("That is not a valid input.");
                }
            }

            return myReader;
        }

        public static String scanString(String question){
            //This method takes the sentence of a question.
            //It asks the question to the player and takes the String user input.
            //It returns the user input as output.
            String myReader = "none";
            boolean error = true;
            while(error == true){
                System.out.println(question);
                Scanner obj = new Scanner(System.in);
                error = false;
                try{
                    myReader = obj.nextLine();
                }catch(Exception e){
                    System.out.println("That is not a valid input.");
                }
            }

            return myReader;
        }

        public static void displayArray(String array []){
            //This method takes an array into input.
            //It displays all word inside of the array and the number of those.
            //It doesn't return anything as output.

            for(int i=0;i<array.length;i++){
                System.out.print(array[i]);
                if(i != 2){
                    System.out.print(", ");
                }
            }
            System.out.println("\n");
        }
    
    
    
}
