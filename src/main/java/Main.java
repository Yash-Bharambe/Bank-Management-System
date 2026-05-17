import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountService service = new AccountService();
        Integer loggedInId = null;

        System.out.println("=== Bank Management System ===");

        while (loggedInId == null) {
            System.out.println("\n1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String initChoiceStr = sc.nextLine();
            int initChoice = Integer.parseInt(initChoiceStr.trim());

            switch (initChoice) {
                //login
                case 1:
                    System.out.print("Enter Account ID: ");
                    String loginIdStr = sc.nextLine();
                    int loginId = Integer.parseInt(loginIdStr.trim());
                    System.out.print("Enter Password: ");
                    String password = sc.nextLine().trim();

                    if (service.login(loginId, password)) {
                        loggedInId = loginId;
                        System.out.println("Login successful! Welcome!");
                    } else {
                        System.out.println("Invalid credentials! If you don't have an account, create one first.");
                    }
                    break;
                // create an account 
                case 2:
                    System.out.print("ID: ");
                    String newIdStr = sc.nextLine();
                    int newId = Integer.parseInt(newIdStr.trim());
                    System.out.print("Name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Initial Balance: ");
                    String balStr = sc.nextLine();
                    double bal = Double.parseDouble(balStr.trim());
                    System.out.print("Password: ");
                    String newPass = sc.nextLine().trim();

                    service.createAccount(newId, name, bal, newPass);
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice! Please enter 1, 2, or 3.");
            }
        }
        //banking menu
        while (true) {
            System.out.println("\n=== Banking Menu ===");

            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Transfer Money");
            System.out.println("5. Transaction History");
            System.out.println("6. Delete Account");
            System.out.println("7. View All Accounts");
            System.out.println("8. Search Accounts");
            System.out.println("9. Exit");

            String choiceStr = sc.nextLine();
            int choice = Integer.parseInt(choiceStr.trim());

            switch (choice) {
                case 1: {
                    System.out.print("Amount: ");
                    String amtStr = sc.nextLine();
                    double amt = Double.parseDouble(amtStr.trim());
                    service.deposit(loggedInId, amt);
                    break;
                }

                case 2: {
                    System.out.print("Amount: ");
                    String amtStr = sc.nextLine();
                    double amt = Double.parseDouble(amtStr.trim());
                    service.withdraw(loggedInId, amt);
                    break;
                }

                case 3: {
                    Account acc = service.getAccount(loggedInId);
                    if (acc != null) {
                        System.out.println("Balance: " + acc.balance);
                    }
                    break;
                }

                case 4: {
                    System.out.print("To Account ID: ");
                    String toIdStr = sc.nextLine();
                    int toId = Integer.parseInt(toIdStr.trim());
                    System.out.print("Amount: ");
                    String amtStr2 = sc.nextLine();
                    double amt2 = Double.parseDouble(amtStr2.trim());
                    service.transfer(loggedInId, toId, amt2);
                    break;
                }

                case 5:
                    service.viewTransactionHistory(loggedInId);
                    break;

                case 6: {
                    System.out.print("Enter Account ID to delete: ");
                    String delIdStr = sc.nextLine();
                    int delId = Integer.parseInt(delIdStr.trim());
                    System.out.print("Enter password to confirm deletion: ");
                    String password = sc.nextLine().trim();

                    boolean deleted = service.deleteAccountWithPassword(delId, password);
                    
                    // If the logged-in user deleted their own account, exit
                    if (deleted && delId == loggedInId) {
                        System.out.println("Your account has been deleted. Goodbye!");
                        System.exit(0);
                    }
                    break;
                }
                case 7:
                    service.viewAllAccounts();
                    break;

                case 8:
                    System.out.print("Enter name to search: ");
                    String searchName = sc.nextLine().trim();
                    service.searchAccounts(searchName);
                    break;

                case 9:
                    System.out.println("Thank you for using our banking system!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}