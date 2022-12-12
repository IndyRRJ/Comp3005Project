package com.company;

import java.rmi.registry.LocateRegistry;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;


public class Main {

    public static void main(String[] args) {

        String database = "lookinnabook1.6";
        String user = "postgres";
        String password = "KagomeKagome";
        ArrayList<ArrayList<String>> cart = new ArrayList<ArrayList<String>>();
        ArrayList<String> login = new ArrayList<String>();

        Connection conn = connect(database, user, password);
        if (conn == null) {
            System.out.println("Please enter the correct login information at the top of main, then relaunch the app");
            System.exit(-1);
        }

        System.out.println("Welcome to Look Inna Book!");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 1 to sign in. Enter 2 to register. Enter 0 to quit.");
        while (true) {

            String input = scanner.nextLine();
            if (input.equals("0")) {
                System.out.println("Goodbye!");
                System.exit(0);
            } else if (input.equals("1")) {
                login = signin(conn);
                if (login != null) {
                    break;
                } else {
                    System.out.println("Error signing in!");
                }
            } else if (input.equals("2")) {
                login = register(conn);
                if (login != null) {
                    break;
                } else {
                    System.out.println("Error registering!");
                }

            }

            else {
                System.out.println("Invalid input!");
            }



        }

        System.out.println("Welcome, " + login.get(4) + "!");

        while (true) {
            restockBooks(conn);
            System.out.println(" ");
            System.out.println("What would you like to do? Input one of the numbers below and press ENTER for the corresponding action.");
            System.out.println("1: Search for book");
            System.out.println("2: Order books");
            System.out.println("3: View order");

            if (login.get(3).equals("Y")) {
                System.out.println("4: Add Book to Store");
                System.out.println("5: Remove Book from Store");
                System.out.println("6: Add Publisher");
                System.out.println("7: Show financial report");
                System.out.println("8: Show sales per report");
                System.out.println("9: Show sales per author report");


            }

            System.out.println("0: Quit");

            System.out.println("Your cart: " +cart);

            String input = scanner.nextLine();
            if (input.equals("0")) {
                System.out.println("Goodbye!");
                break;
                //System.exit(0);
            } else if (input.equals("1")) {
                searchBooks(conn, cart);

            } else if (input.equals("2")) {
                checkout(conn, login.get(0), cart);

            } else if (input.equals("3")) {
                viewOrder(conn);

            }

            else {

                if (login.get(3).equals("Y")) {
                    if (input.equals("4")) {
                        addBook(conn);
                    } else if (input.equals("5")) {
                        removeBook(conn);
                    } else if (input.equals("6")) {
                        addPublisher(conn);
                    } else if (input.equals("7")) {
                        generateFinanceReport(conn);
                    } else if (input.equals("8")) {
                        generateGenrePopularityReport(conn);
                    } else if (input.equals("9")) {
                        generateAuthorPopularityReport(conn);
                    }
                } else {
                    System.out.println("Invalid input!");
                }



            }



        }


        disconnect(conn);
    }

    //connect to the database with this function and keep main() clean
    public static Connection connect(String database, String user, String password) {
        try {
            String url = "jdbc:postgresql://localhost:5432/" + database;
            return DriverManager.getConnection(url,user, password);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    // i wonder what this does
    public static void disconnect(Connection conn) {
        try {
            conn.close();
            System.out.println("Disconnected!");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    //Sends inputted and prints results, might be too generic but could maybe just have it call other functions
    public static ArrayList<ArrayList<String>> sendStatement(Connection conn, String statement) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(statement);

            ArrayList<ArrayList<String>> theData = new ArrayList<ArrayList<String>>();

            ResultSetMetaData rsmd = rs.getMetaData();
            //System.out.print(rsmd.getColumnCount());

            int numColumns = rsmd.getColumnCount();

            while (rs.next()) {
                int i = 1;
                ArrayList<String> theRow = new ArrayList<String>();
                while (i <= numColumns) {
                    theRow.add(rs.getString(i));
                    i++;
                }
                theData.add(theRow);
            }

            rs.close();
            st.close();

            return theData;

        } catch (Exception e){
            //System.out.println(e); //comment this out after
            return null;
        }

    }

    //sign in to an existing account
    public static ArrayList<String> signin(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<ArrayList<String>> result;

        while (true) {
            System.out.println("Enter your username: ");
            String input = scanner.nextLine();
            result = sendStatement(conn, "SELECT * FROM USERS WHERE username = '"+ input + "'");
            if (result == null || result.size() == 0) {
                System.out.println("Incorrect user info!");
            } else {
                return result.get(0);
            }
        }

    }

    //create a new user account.
    public static  ArrayList<String> register(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<ArrayList<String>> result;
        String username;
        String crnum;
        String addr;
        String fname;
        String lname;

        while (true) {
            System.out.println("Enter a username, must be 20 or less characters: ");
            username = scanner.nextLine();
            if (username.length() > 20 || username.length() < 1) {
                System.out.println("Invalid length!");
                continue;
            } else if (sendStatement(conn, "SELECT * FROM USERS WHERE username =" + username) != null) {
                System.out.println("Username taken!");
                continue;
            }
            break;

        }
        while (true) {
            System.out.println("Enter your credit card number, up to 12 characters");
            crnum = scanner.nextLine();
            if (crnum.length() > 12 || crnum.length() < 1) {
                System.out.println("Invalid length!");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Enter your address, up to 30 characters");
            addr = scanner.nextLine();
            if (addr.length() > 30 || addr.length() < 1) {
                System.out.println("Invalid length!");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Enter your first name, up to 15 characters");
            fname = scanner.nextLine();
            if (fname.length() > 15 || fname.length() < 1) {
                System.out.println("Invalid length!");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Enter your last name, up to 15 characters");
            lname = scanner.nextLine();
            if (lname.length() > 15 || lname.length() < 1) {
                System.out.println("Invalid length!");
                continue;
            }
            break;
        }

        String toSend = "INSERT INTO users (username, creditnum, address, admi, fname, lname) " +
                "VALUES (\'" + username +"\',\'" + crnum +"\',\'" + addr +"\',\'" + "N\',\'" + fname +"\',\'" + lname + "\')";
        //System.out.println(toSend);

        sendStatement(conn, toSend);

        result = sendStatement(conn, "SELECT * FROM USERS WHERE username ='" + username +"'" );


        return result.get(0);

    }

    //function to find a specific book by filling out search criteria
    public static void searchBooks(Connection conn, ArrayList<ArrayList<String>> cart) {
        Scanner scanner = new Scanner(System.in);
        String searchType;
        String searchTerm = "none";


        System.out.println("Input the number corresponding to the category you wish to search using:");
        System.out.println("1: Title");
        System.out.println("2: ISBN");
        System.out.println("3: Page Count");
        System.out.println("4: Price");
        System.out.println("0: Return to menu");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("0")) {
                return;
            } else if (input.equals("1")) {
                searchTerm = "bname"; //already works
                break;
            } else if (input.equals("2")) {
                searchTerm = "isbn"; //already works
                break;
            }   else if (input.equals("3")) {
                searchTerm = "pagenum"; //alreayd works
                break;
            } else if (input.equals("4")) {
                searchTerm = "price";
                break;
            }
            else {
                System.out.println("Invalid input!");
            }
        }

        String input;
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        switch (searchTerm) {

            case "none":
                System.out.println("Error!");
            case "lname":
                System.out.println("Enter the author's last name: ");
                input = scanner.nextLine();

                String statement = "SELECT * FROM author WHERE "+searchTerm+" = '" + input + "';";

                System.out.println(statement);

                results = sendStatement(conn, statement);

                System.out.println(results);

                ArrayList<String> author = new ArrayList<String>();
                if (results.size() == 0) {
                    System.out.println("Author not found!");
                    return;
                }
                if (results.size() > 1) {
                    author = pickOneFromMany(results);
                } else {
                    author = results.get(0);
                }

                results = sendStatement(conn,"(SELECT isbn FROM writes WHERE aid = '" + author.get(3) + "');");
                System.out.println(results);

                results = sendStatement(conn,"SELECT * FROM book WHERE isbn = (SELECT isbn FROM writes WHERE aid = '" + author.get(3) + "');");
                System.out.println(results);
                break;

            default:
                System.out.println("Enter the relevant info of that search type:");
                input = scanner.nextLine();
                results = sendStatement(conn,"SELECT * FROM book WHERE "+searchTerm+" = '" + input + "'");

                //System.out.println(results);

        }

        ArrayList<String> result = new ArrayList<String>();


        if (results.size() < 1) {
            System.out.println("No results found");
        }
        else {
            if (results.size() > 1) {
                result = pickOneFromMany(results);
            } else {
                result = results.get(0);
            }

            System.out.println("Name: " + result.get(3) + " ISBN: "+ result.get(0) + " Price: $" + result.get(1) + " Quantity: " + result.get(7));
            System.out.println("How many would you like to add to cart? Enter 0 to add zero.");
            while (true) {
                input = scanner.nextLine();
                try{
                    int number = Integer.parseInt(input);

                    if (number > Integer.parseInt(result.get(7))) {
                        System.out.println("That's too many");
                        continue;
                    } else if (number <= 0) {
                        break;
                    } else {
                        result.set(6, Integer.toString(number));
                        cart.add(result);
                        break;
                    }

                }
                catch (NumberFormatException ex){
                    System.out.println("Invalid input!");
                    continue;
                }
            }
        }

        //pick search type and search term, then send search request incorporating that
        //if multiple results, have user choose which one
        //once single result left ask if want to add to cart
        //choose quantity and add to cart
        System.out.println("Returning to main menu...");
        return;
    }

    //function to checkout
    public static void checkout(Connection conn, String login, ArrayList<ArrayList<String>> cart) {
        //create order in here probably, probably call createorder
        //could also have auto-restock done here
            //send the email here too (just output a string to represent email being sent)

        if (cart.size() == 0) {
            System.out.println("Nothing to check out!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String crnum;
        String addr;

        while (true) {
            System.out.println("Enter your credit card number, up to 12 characters");
            crnum = scanner.nextLine();
            if (crnum.length() > 12 || crnum.length() < 1) {
                System.out.println("Invalid length!");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Enter your address, up to 30 characters");
            addr = scanner.nextLine();
            if (addr.length() > 30 || addr.length() < 1) {
                System.out.println("Invalid length!");
                continue;
            }
            break;
        }

        ArrayList<ArrayList<String>> results = sendStatement(conn, "SELECT ordernum FROM ORDERS ORDER BY ordernum DESC");
        //System.out.println(results);
        String ordernum = Integer.toString(Integer.parseInt(results.get(0).get(0)  ) + 1);

        results = sendStatement(conn, "SELECT trackingnum FROM ORDERS ORDER BY trackingnum DESC");
        //System.out.println(results);
        String trackingnum = Integer.toString(Integer.parseInt(results.get(0).get(0) ) + 1);

        float cost = 0;
        for (int i = 0; i < cart.size(); i++) {
            cost += Float.parseFloat(cart.get(i).get(1))  * Float.parseFloat(cart.get(i).get(6));
        }

        results = sendStatement(conn, "INSERT INTO orders (ordernum, costs, trackingnum, creditnum, address, username) " +
                "VALUES (\'" + ordernum +"\',\'" + Float.toString(cost) +"\',\'" + trackingnum +"\',\'" + crnum +"\',\'" + addr +"\',\'" + login + "\')");


        int size = cart.size();

        for (int i = 0; i < size; i++) {
            //gotta decrease quantities
            String isbn = cart.get(0).get(0);
            String quantity = cart.get(0).get(6);

            //System.out.println(cart.get(0).get(0));
            results = sendStatement(conn,"SELECT QTY FROM book WHERE isbn = '" +isbn + "'");
            //System.out.println(results);



            String newquantity = Integer.toString(Integer.parseInt(results.get(0).get(0)) - Integer.parseInt(quantity));

            results = sendStatement(conn, "UPDATE BOOK SET qty = '" + newquantity +"' WHERE isbn = '" + isbn + "';"  );
            results = sendStatement(conn, "UPDATE BOOK SET totalsold = totalsold + '" + quantity +"' WHERE isbn = '" + isbn + "';"  );
            results = sendStatement(conn, "UPDATE genre SET sales = sales + '" + quantity +"' WHERE genre = (SELECT genre FROM isgenre where isbn = '" + isbn + "');"  );
            results = sendStatement(conn, "UPDATE author SET sales = sales + '" + quantity +"' WHERE aid = (SELECT aid from writes where isbn='" + isbn + "');"  );

            //and create "ordered" for each book

            results = sendStatement(conn, "INSERT INTO ordered (isbn, ordernum, qty) " +
                    "VALUES (\'" + isbn +"\',\'" + ordernum +"\',\'" + quantity + "\')"  );


            cart.remove(0);
        }


        System.out.println("Thank you for shopping! Your order number is " + ordernum + ".");
    }

    //view existing order
    public static void viewOrder(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your order number: ");
        String ordernum = scanner.nextLine();

        ArrayList<ArrayList<String>> results = sendStatement(conn, "SELECT * FROM ORDERS WHERE ordernum = '" + ordernum + "'");
        if (results == null || results.size() == 0) {
            System.out.println("Invalid order number!");
            return;
        }

        System.out.println("Order number " + results.get(0).get(0) + " has tracking number " + results.get(0).get(2) + " and is in progress.");

    }

    //function to add a book to the bookstore
    public static void addBook(Connection conn) {


        DecimalFormat df = new DecimalFormat("0.00");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the isbn: ");

        String isbn = scanner.nextLine();

        if (isbn.length() != 9) {
            System.out.println("Invalid isbn!");
            return;
        }

        ArrayList<ArrayList<String>> results = sendStatement(conn, "SELECT * FROM BOOK WHERE isbn = '" + isbn + "'");

        if (results.size() != 0) { //book already exists
            System.out.println("This book already exists! Return to menu");
            return;
        }

        System.out.println("Enter the publisher email: ");
        String pubemail = scanner.nextLine();
        if (pubemail.length() > 30 || pubemail.length() < 1) {
            System.out.println("Invalid publisher email");
            return;
        }

        //check for publisher existence
        if (sendStatement(conn, "SELECT * FROM publisher WHERE email = '" + pubemail + "'").size() == 0) {
            System.out.println("Publisher does not exist! Create publisher first!");
            return;
        }




        System.out.println("Enter the book name: ");
        String bname = scanner.nextLine();
        if (bname.length() > 30 || bname.length() < 1) {
            System.out.println("Invalid book name!");
            return;
        }

        System.out.println("Enter the price: ");
        float price = Float.parseFloat(scanner.nextLine());

        if (price >= 1000000000) {
            System.out.println("Invalid price!");
            return;
        }

        System.out.println("Enter the supplier price: ");
        float supplierprice = Float.parseFloat(scanner.nextLine());

        if (supplierprice >= 1000000000) {
            System.out.println("Invalid supplier price!");
            return;
        }

        System.out.println("Enter the number of pages: ");
        int pagecount = Integer.parseInt(scanner.nextLine());

        if (pagecount >= 10000) {
            System.out.println("Invalid page count!");
            return;
        }

        System.out.println("Enter the publisher percentage: ");
        float percentage = Float.parseFloat(scanner.nextLine());

        if (percentage >= 1.00) {
            System.out.println("Invalid supplier percentage!");
            return;
        }

        System.out.println("Enter the auto-restock threshold: ");
        float restock = Float.parseFloat(scanner.nextLine());

        if (restock >= 100) {
            System.out.println("Invalid restock threshold");
            return;
        }

        System.out.println("Enter the quantity to buy: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (quantity >= 10000 ) {
            System.out.println("Invalid quantity, too high");
            return;
        }



        String statement = "INSERT INTO book (isbn, price, supplierprice, bname, pagenum, percentage, restock, qty, expenditure, pubemail, totalsold)" +
                "values ('"+ isbn + "','" + Float.toString(price) + "','" + Float.toString(supplierprice) + "','" + bname + "','" + Integer.toString(pagecount) + "','" + Float.toString(percentage)+ "','" + restock  + "','"
                + Integer.toString(quantity) + "','"+ Float.toString(supplierprice*quantity) +"','" + pubemail + "', '0');";

        //System.out.println(statement);

        results = sendStatement(conn, statement  );


        //handle authors
        System.out.println("How many authors does this book have? Enter an integer >= 0");
        int numOfNums = Integer.parseInt(scanner.nextLine());

        int i = 0;
        while (i < numOfNums) {
            System.out.println("Enter the Author id (9 characters) ");
            String aid = scanner.nextLine();
            if (aid.length() != 9) {
                System.out.println("Invalid length");
                continue;
            }
            if ( sendStatement(conn, "SELECT * FROM author WHERE aid = '" + aid + "'").size() != 0 ) { // author exists
                System.out.println("Author already exists! Adding author to book!");
                statement = "INSERT INTO writes (isbn, aid)" +
                        "values ('"+ isbn + "','" + aid + "');";
                results = sendStatement(conn, statement  );
            } else {
                String fname = "";
                String lname = "";
                while (true) {
                    System.out.println("Enter author's first name, up to 15 characters");
                    fname = scanner.nextLine();
                    if (fname.length() > 15 || fname.length() < 1) {
                        System.out.println("Invalid length!");
                        continue;
                    }
                    break;
                }
                while (true) {
                    System.out.println("Enter author's last name, up to 15 characters");
                    lname = scanner.nextLine();
                    if (lname.length() > 15 || lname.length() < 1) {
                        System.out.println("Invalid length!");
                        continue;
                    }
                    break;
                }
                System.out.println("Creating author and adding to book!");
                statement = "INSERT INTO author (fname, sales, lname, aid)" +
                        "values ('"+ fname + "','0','" + lname + "','" + aid + "');";
                results = sendStatement(conn, statement  );
                statement = "INSERT INTO writes (isbn, aid)" +
                        "values ('"+ isbn + "','" + aid + "');";
                results = sendStatement(conn, statement  );
            }


            i++;
        }

        //handle genres
        System.out.println("How many genres does this book have? Enter and integer >= 0");
        numOfNums = Integer.parseInt(scanner.nextLine());

        i = 0;
        while (i < numOfNums) {
            System.out.println("Enter the Genre name");
            String genre = scanner.nextLine();

            if ( sendStatement(conn, "SELECT * FROM genre WHERE genre = '" + genre + "'").size() == 0 ) { // genre does not exist
                System.out.println("Genre invalid!");

            } else {
                statement = "INSERT INTO isgenre (isbn, genre)" +
                        "values ('"+ isbn + "','" + genre + "');";
                results = sendStatement(conn, statement  );
            }


            i++;
        }

        System.out.println("Book created!");


    }

    public static void restockBooks(Connection conn) {
        ArrayList<ArrayList<String>> results = sendStatement(conn, "UPDATE BOOK SET  expenditure = (expenditure + (supplierprice*restock*5))  WHERE (qty < restock) ");
        results = sendStatement(conn, "UPDATE BOOK SET  qty = (qty + (restock*5))  WHERE (qty < restock) ");
        System.out.println("Restock orders sent!");
    }

    //remove a book from the bookstore
    public static void removeBook(Connection conn) {
        //see delete call commented out in main
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the isbn of the book to delete: ");

        String isbn = scanner.nextLine();

        if (isbn.length() != 9) {
            System.out.println("Invalid isbn!");
            return;
        }

        ArrayList<ArrayList<String>> results = sendStatement(conn, "DELETE FROM writes WHERE isbn = '" + isbn + "';");
        results = sendStatement(conn, "DELETE FROM isgenre WHERE isbn = '" + isbn + "';");
        results = sendStatement(conn, "DELETE FROM ordered WHERE isbn = '" + isbn + "';");
        results = sendStatement(conn, "DELETE FROM book WHERE isbn = '" + isbn + "';");


        System.out.println("Book information deleted!");


    }

    //create a publisher in the table
    public static void addPublisher(Connection conn) {
        //create the publisher, check for existence,
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter the publisher email: ");
        String email = scanner.nextLine();
        if (email.length() > 30 || email.length() < 1) {
            System.out.println("Invalid email");
            return;
        } else if ( sendStatement(conn, "SELECT * FROM publisher WHERE email = '" + email + "'").size() != 0 ) { //check to see if email taken
            System.out.println("email already taken!");
            return;
        }

        System.out.println("Enter the publisher name: ");
        String name = scanner.nextLine();
        if (name.length() > 50 || name.length() < 1) {
            System.out.println("Invalid name, too long or short!");
            return;
        }

        System.out.println("Enter the publisher bank account number: ");
        String banknum = scanner.nextLine();
        if (banknum.length() > 12 || banknum.length() < 1) {
            System.out.println("Invalid bank account number!");
            return;
        }

        System.out.println("Enter the publisher address: ");
        String address = scanner.nextLine();
        if (address.length() > 30 || address.length() < 1) {
            System.out.println("Invalid address");
            return;
        }

        //handle phone numbers
        System.out.println("How many phone numbers does this publisher have? Enter a whole number >= 0");
        int numOfNums = Integer.parseInt(scanner.nextLine());

        ArrayList<String> numbers = new ArrayList<String>();
        int i = 0;
        while (i < numOfNums) {
            System.out.println("Enter a phone number: ");
            String phonenum = scanner.nextLine();
            if (phonenum.length() > 12) {
                System.out.println("Phone number too long!");
                continue;
            }
            numbers.add(phonenum);
            i++;
        }

        String statement = "INSERT INTO publisher (email, pname, banknum, address)" +
                "values ('"+ email + "','" + name + "','" + banknum + "','" + address + "');";

        System.out.println(statement);

        ArrayList<ArrayList<String>> results = sendStatement(conn, statement  );

        for (int j = 0; j < numOfNums; j++) {
            statement = "INSERT INTO publisherphone (email, pphone)" +
                    "values ('"+ email + "','" + numbers.get(j) + "');";
            results = sendStatement(conn, statement  );
        }

    }

    public static void generateFinanceReport(Connection conn) {
        //most of the fanciness comes from the sql commands

        String statement = "SELECT price, percentage, expenditure, totalsold FROM book";

        ArrayList<ArrayList<String>> results = sendStatement(conn,statement);
        //System.out.println(results);

        double revenue = 0.00;
        double expenses = 0.00;


        for (ArrayList<String> element : results) {
            double price = Double.parseDouble(element.get(0));
            double percent = Double.parseDouble(element.get(1));
            double expenditure = Double.parseDouble(element.get(2));
            double totalsold = Double.parseDouble(element.get(3));

            revenue += price * (1-percent) * totalsold;
            expenses += expenditure;
        }

        double total = revenue - expenses;

        System.out.println("Total   income: $" + revenue);
        System.out.println("Total expenses: $" + expenses);
        System.out.println("Total   profit: $" + total);

    }

    public static void generateGenrePopularityReport(Connection conn) {
        //most of the fanciness comes from the sql commands

        ArrayList<ArrayList<String>> results = sendStatement(conn,"SELECT * FROM genre");
        System.out.println("Genre       | Sales");

        for (ArrayList<String> element : results) {
            System.out.println(element.get(0) + " | " + element.get(1));
        }

    }

    public static void generateAuthorPopularityReport (Connection conn) {
        //most of the fanciness comes from the sql commands
        ArrayList<ArrayList<String>> results = sendStatement(conn,"SELECT * FROM author");
        System.out.println("Author       | Sales");

        for (ArrayList<String> element : results) {
            System.out.println(element.get(0) + " " + element.get(2) + " | " + element.get(1));
        }
    }

    public static ArrayList<String> pickOneFromMany(ArrayList<ArrayList<String>> many) {
        System.out.println("Multiple results found but only 1 may be selected:");
        int size = many.size();

        for (int i = 0; i < size; i++) {
            System.out.println(i + ": " + many.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the number corresponding to the selection you want from above:");

        while (true) {
            String input = scanner.nextLine();
            try{
                int number = Integer.parseInt(input);

                if (number < 0 || number >= size) {
                    System.out.println("Invalid input!");
                    continue;
                }

                ArrayList<String> one = many.get(number);
                return one;

            }
            catch (NumberFormatException ex){
                System.out.println("Invalid input!");
                continue;
            }
        }


    }

}
