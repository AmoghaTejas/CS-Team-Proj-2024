import java.util.ArrayList;

public interface UserInterface {
    String getUserName();
    void setUserName(String userName);
    String getPassword();
    void setPassword(String password);
    ArrayList<String> getListOfFriends();
    void setListOfFriends(ArrayList<String> listOfFriends);
    ArrayList<String> getBlockedUsers();
    void setBlockedUsers(ArrayList<String> blockedUsers);
    void setMothersMaidenName(String mothersMaidenName);
    String getMothersMaidenName();
    User createNewUser(String userName, String password, String mothersMaidenName);
    String login(String userName, String password);
    boolean addFriend(String friendName,String userName);
    void deleteFriends(String friendNames, String username);
    boolean seeFriends(String username);
    User forgotPassword(String userName, String mothersMaidenName, String newPassword);
    void viewMessages(String friendName, String userName);
    void messagingText(String userName,String friendName, String message);

}