package Activity;


import Models.Users;

public class GlobalVar {

    public static Users currentUser = new Users(); //creating one global variable to get from current user
    //when login done then we will fetch all user data from Users table and save to currentUser...
}
