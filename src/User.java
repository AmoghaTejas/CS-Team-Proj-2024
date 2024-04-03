import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class User implements UserInterface {
    //fields
    private String userName;
    private String password;
    private String mothersMaidenName;
    private ArrayList<String> listOfFriends;
    private ArrayList<String> blockedUsers;
    //constructor
    public User(String userName, String password, String mothersMaidenName) {
        this.userName = userName;
        this.password = password;
        this.mothersMaidenName = mothersMaidenName;
        this.listOfFriends = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
    }

    // empty constructor - all Strings, ArrayLists set to null, ints set to -1, booleans set to false
    public User() {
        this.userName = "";
        this.password = "";
        this.mothersMaidenName = "";
        this.listOfFriends = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getListOfFriends() {
        return this.listOfFriends;
    }

    public void setListOfFriends(ArrayList<String> listOfFriends) {
        this.listOfFriends = listOfFriends;
    }

    public ArrayList<String> getBlockedUsers() {
        return this.blockedUsers;
    }

    public void setBlockedUsers(ArrayList<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setMothersMaidenName(String mothersMaidenName) {
        this.mothersMaidenName = mothersMaidenName;
    }

    public String getMothersMaidenName() {
        return mothersMaidenName;
    }
    public User createNewUser(String userName, String password, String mothersMaidenName) {
        ArrayList<String> usersData = new ArrayList<>();
        File file = new File(userName + ".txt");
        if (file.exists()) {
            System.out.println("A user exists with same username, try a different username!");
            return null;
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            this.userName = userName;
            this.password = password;
            this.mothersMaidenName = mothersMaidenName;
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.write(password);
            bufferedWriter.newLine();
            bufferedWriter.write(mothersMaidenName);
            bufferedWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("AllUsers.txt"));
            String info;
            while ((info = bufferedReader.readLine()) != null) {
                usersData.add(info);
            }
            bufferedReader.close();
        } catch (IOException exception) {
            File file1 = new File("AllUsers.txt");
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("AllUsers.txt"));
                bufferedWriter.write(userName + ".txt");
                bufferedWriter.newLine();
                User user = new User(userName, password, mothersMaidenName);
                bufferedWriter.close();
                try {
                    BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(userName + "friendsList.txt"));
                    bufferedWriter1.write("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return user;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        usersData.add(userName + ".txt");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("AllUsers.txt"));
            for (String string : usersData) {
                bufferedWriter.write(string);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
        try {
            BufferedReader bufferedReader1 = new BufferedReader(new FileReader("AllUsers.txt"));
            String info;
            while ((info = bufferedReader1.readLine()) != null) {
                usersData.add(info);
            }
            bufferedReader1.close();
            User user = new User(userName, password, mothersMaidenName);
            try {
                BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(userName + "friendsList.txt"));
                bufferedWriter1.write("");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return user;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String login(String userName, String password) {
        int count = 0;
        int count1 = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new BufferedReader(new
                    FileReader(userName + ".txt")));
            String reader;
            while ((reader = bufferedReader.readLine()) != null) {
                count++;
                if (count == 1 && reader.equalsIgnoreCase(userName)) {
                    count1++;
                }
                if (count == 2 && reader.equals(password)) {
                    count1++;
                }
            }
            bufferedReader.close();
            if (count1 == 2) {
                System.out.println("Login successful !!");
                setUserName(userName);
                setPassword(password);
                setListOfFriends(new ArrayList<>());
                return "success";
            } else {
                System.out.println("Username or password does not exist !");
                return "failure";
            }
        } catch (IOException exception) {
            System.out.println("Make sure the user exists !!");
            return "failure";
        }
    }
    public boolean addFriend(String friendName,String userName) {
        seeFriends1(userName);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(userName + "friendsList.txt"));
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                listOfFriends.add(string);
            }
            bufferedReader.close();
        } catch (FileNotFoundException exception) {
            File file = new File(userName + "friendsList.txt");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("AllUsers.txt"));
            String info;
            while ((info = bufferedReader.readLine()) != null) {
                if (info.contains(friendName)) {
                    listOfFriends.add(friendName);
                }
            }
            if (listOfFriends == null) {
                System.out.println("Make sure the user exists");
            }
            bufferedReader.close();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(userName + "friendsList.txt"));
                for (String information : listOfFriends) {
                    writer.write(information);
                    writer.newLine();
                }
                writer.close();
                return true;
            } catch (IOException exception) {
                exception.printStackTrace();
                return false;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    // enter the friends' names to delete separated by a comma
    public void deleteFriends(String friendNames, String userName) {
        String[] splitNames;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                    userName + "friendsList.txt"));
            for (String string1 : listOfFriends) {
                if (friendNames.equalsIgnoreCase(string1)) {
                } else {
                    bufferedWriter.write(string1);
                }
            }
            System.out.println("Friend removed ");
            bufferedWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public boolean seeFriends(String userName) {
        listOfFriends = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userName + "friendsList.txt"));
            String string;
            while ((string = reader.readLine()) != null) {
                listOfFriends.add(string);
                System.out.println(string);
            }
            reader.close();
            return true;
        } catch (FileNotFoundException exception) {
            System.out.println("Make sure you have friends");
            exception.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean seeFriends1(String userName) {
        listOfFriends = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userName + "friendsList.txt"));
            String string;
            while ((string = reader.readLine()) != null) {
                listOfFriends.add(string);
            }
            reader.close();
            return true;
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public User forgotPassword(String userName, String mothersMaidenName, String newPassword) {
        int count = 0;
        int count1 = 0;
        int count3 = 0;
        try {
            count ++;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(userName + ".txt"));
            String info;
            while ((info = bufferedReader.readLine()) != null) {
                count ++;
                if (count == 1 && info.equalsIgnoreCase(userName)) {
                    count1 ++;
                }
                if (count == 3 && info.equals(mothersMaidenName)) {
                    count1 ++;
                }
            }
            bufferedReader.close();
            if (count1 == 2) {
                System.out.println("Password changed !");
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(userName + ".txt"));
                    count3 ++;
                    bufferedWriter.write(userName);
                    bufferedWriter.newLine();
                    bufferedWriter.write(newPassword);
                    bufferedWriter.write(mothersMaidenName);
                    bufferedWriter.close();
                    User user = new User(userName,newPassword,mothersMaidenName);
                    return user;
                } catch (IOException exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
            return null;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public void viewMessages(String friendName, String userName) {
        File file = new File(userName + "messaging" + friendName + ".txt");
        File file1 = new File(friendName + "messaging" + userName + ".txt");
        if ( !file.exists() && !file1.exists() ) {
            System.out.println("You do not have any messages history with this user !");
        }
        if ( file.exists() ) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String info;
                while ((info = bufferedReader.readLine()) != null) {
                    System.out.println(info);
                }
                bufferedReader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else if (file1.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file1));
                String info;
                while ((info = bufferedReader.readLine()) != null) {
                    System.out.println(info);
                }
                bufferedReader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
    public void messagingText(String userName,String friendName, String message) {
        seeFriends1(userName);
        int count = 0;
        for (String in : listOfFriends) {
            if (in.trim().contains(friendName)) {
                count ++;
            }
        }
        if (count >= 1) {

        } else if (count != 1) {
            throw new IllegalArgumentException("Friend not found");
        }
        File file = new File(userName + "messaging" + friendName + ".txt");
        File file1 = new File(friendName + "messaging" + userName + ".txt");
        ArrayList<String> messages = new ArrayList<>();
        if ( !file.exists() && !file1.exists() ) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if ( file.exists() ) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String info;
                while ((info = bufferedReader.readLine()) != null) {
                    messages.add(info);
                }
                bufferedReader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            messages.add(message);
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                for (String string : messages) {
                    bufferedWriter.write("sent by " + this.userName + " - " + string);
                    bufferedWriter.newLine();
                }
                System.out.println("Message sent");
                bufferedWriter.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else if (file1.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file1));
                String info;
                while ((info = bufferedReader.readLine()) != null) {
                    messages.add(info);
                }
                bufferedReader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            messages.add(message);
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                for (String string : messages) {
                    bufferedWriter.write("sent by " + this.userName + " - " + string);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
    public static void main(String[] args ) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        boolean exit1 = false;
        System.out.println("Welcome to the User Management System!");
        while (!exit) {
            System.out.println("Choose an option:");
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.println("Enter your choice (1/2/3): ");
            String choice = sc.nextLine();
            if ( !choice.equals("1") && !choice.equals("2") && !choice.equals("3") ) {
                System.out.println("Enter valid input");
            }
            if (choice.equals("1")) {
                System.out.println("Enter the username");
                String username = sc.nextLine();
                System.out.println("Enter the password");
                String password = sc.nextLine();
                System.out.println("Enter your mother's maiden name");
                String mothersMaidenName = sc.nextLine();
                User user = new User();
                User user1 = user.createNewUser(username,password,mothersMaidenName);
                if (user1 == null) {

                } else {
                    System.out.println("User created");
                }
            } else if (choice.equals("2")) {
                System.out.println("Enter the username");
                String username = sc.nextLine();
                System.out.println("Enter the password");
                String password = sc.nextLine();
                User user = new User();
                String result = user.login(username,password);
                if (result.equalsIgnoreCase("success")) {
                    User user1 = new User(username, password,"");
                    while (!exit1) {
                        System.out.println("1. Add Friend");
                        System.out.println("2. Remove Friend");
                        System.out.println("3. Send message");
                        System.out.println("4. See friends");
                        System.out.println("5. View messages");
                        System.out.println("Enter your choice (1/2/3/4): ");
                        String choice1 = sc.nextLine();
                        if (!choice1.equals("1") && !choice1.equals("2") && !choice1.equals("3") &&
                                !choice1.equals("4") && !choice1.equals("5")) {
                            System.out.println("Enter valid input");
                        }
                        if (choice1.equals("1")) {
                            System.out.println("Enter your username");
                            String username1 = sc.nextLine();
                            System.out.println("Enter the name of the friend");
                            String friendName = sc.nextLine();
                            if (user.addFriend(friendName,username1)) {
                                System.out.println("Friend added");
                            }
                        } else if (choice1.equals("2")) {
                            System.out.println("Enter your username");
                            String use = sc.nextLine();
                            System.out.println("Enter Friend name");
                            String friendName = sc.nextLine();
                            user.deleteFriends(friendName,use);
                        } else if (choice1.equals("3")) {
                            System.out.println("Enter your username");
                            String us = sc.nextLine();
                            System.out.println("Enter friend name");
                            String friendName = sc.nextLine();
                            System.out.println("Enter the message you want to send");
                            String message = sc.nextLine();
                            user.messagingText(us,friendName,message);
                        } else if (choice1.equals("4")) {
                            System.out.println("Enter the username");
                            String name = sc.nextLine();
                            user.seeFriends(name);
                        } else if (choice1.equals("5")) {
                            System.out.println("Enter your username");
                            String userName = sc.nextLine();
                            System.out.println("Enter your friend's username");
                            String friendName = sc.nextLine();
                            user.viewMessages(friendName, userName);
                        } else {
                            System.out.println("Invalid input");
                            break;
                        }
                    }
                }
            } else if (choice.equals("3")) {
                System.out.println("Thank you");
                break;
            }
        }
        sc.close();
    }
}
