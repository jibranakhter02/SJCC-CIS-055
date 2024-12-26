package com.egyptianexample;

import java.util.*;
import org.json.simple.*;

public class EgyptianPyramidsAppExample {

  // I've used two arrays here for O(1) reading of the pharaohs and pyramids.
  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;

  public static void main(String[] args) {
    // create and start the app
    EgyptianPyramidsAppExample app = new EgyptianPyramidsAppExample();
    app.start();
  }

  // main loop for app
  public void start() {
    Scanner scan = new Scanner(System.in);
    Character command = '_';

    // loop until user quits
    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      executeCommand(scan, command);
    }
  }

  // constructor to initialize the app and read commands
  public EgyptianPyramidsAppExample() {
    // read egyptian pharaohs
    String pharaohFile = "C:\\Users\\jibra\\Naseffs-Egyptian-Pyramids-App\\demo\\src\\main\\java\\com\\egyptianexample\\pharaoh.json";
    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);

    // create and initialize the pharaoh array
    initializePharaoh(pharaohJSONArray);

    // read pyramids
    String pyramidFile = "C:\\Users\\jibra\\Naseffs-Egyptian-Pyramids-App\\demo\\src\\main\\java\\com\\egyptianexample\\pyramid.json";
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

    // create and initialize the pyramid array
    initializePyramid(pyramidJSONArray);
  }

  // initialize the pharaoh array
  private void initializePharaoh(JSONArray pharaohJSONArray) {
    pharaohArray = new Pharaoh[pharaohJSONArray.size()];

    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);

      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();

      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohArray[i] = p;
    }
  }

  // initialize the pyramid array
  private void initializePyramid(JSONArray pyramidJSONArray) {
    pyramidArray = new Pyramid[pyramidJSONArray.size()];

    for (int i = 0; i < pyramidJSONArray.size(); i++) {
      JSONObject o = (JSONObject) pyramidJSONArray.get(i);

      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
      String[] contributors = new String[contributorsJSONArray.size()];

      for (int j = 0; j < contributorsJSONArray.size(); j++) {
        contributors[j] = contributorsJSONArray.get(j).toString();
      }

      Pyramid p = new Pyramid(id, name, contributors);
      pyramidArray[i] = p;
    }
  }

  // get an integer from a json object, and parse it
  private Integer toInteger(JSONObject o, String key) {
    String s = o.get(key).toString();
    return Integer.parseInt(s);
  }

  // get first character from input
  private static Character menuGetCommand(Scanner scan) {
    String rawInput = scan.nextLine();
    return rawInput.isEmpty() ? '_' : rawInput.toLowerCase().charAt(0);
  }

  // print all pharaohs
  private void printAllPharaoh() {
    for (int i = 0; i < pharaohArray.length; i++) {
      printMenuLine();
      pharaohArray[i].print();
      printMenuLine();
    }
  }

  // print all pyramids and contributors
  private void printAllPyramids() {
    for (int i = 0; i < pyramidArray.length; i++) {
      printMenuLine();
      Pyramid p = pyramidArray[i];
      System.out.printf("Pyramid %s\n", p.name);
      System.out.printf("\tid: %d\n", p.id);
      System.out.println("\tContributors:");
      for (String contributor : p.contributors) {
        System.out.printf("\t\t- %s\n", contributor);
      }
      printMenuLine();
    }
  }

  // print specific pharaoh by ID
  private void printPharaohById(Scanner scan) {
    System.out.print("Enter Pharaoh ID: ");
    int id = Integer.parseInt(scan.nextLine());
    boolean found = false;

    for (int i = 0; i < pharaohArray.length; i++) {
      if (pharaohArray[i].id == id) {
        pharaohArray[i].print();
        found = true;
        break;
      }
    }

    if (!found) {
      System.out.println("Pharaoh with ID " + id + " not found.");
    }
  }

  // print specific pyramid by ID
  private void printPyramidById(Scanner scan) {
    System.out.print("Enter Pyramid ID: ");
    int id = Integer.parseInt(scan.nextLine());
    boolean found = false;

    for (int i = 0; i < pyramidArray.length; i++) {
      if (pyramidArray[i].id == id) {
        Pyramid p = pyramidArray[i];
        System.out.printf("Pyramid %s\n", p.name);
        System.out.printf("\tid: %d\n", p.id);
        System.out.println("\tContributors:");
        for (String contributor : p.contributors) {
          System.out.printf("\t\t- %s\n", contributor);
        }
        found = true;
        break;
      }
    }

    if (!found) {
      System.out.println("Pyramid with ID " + id + " not found.");
    }
  }

  // print requested pyramids without duplicates
  private void printUniquePyramids(Scanner scan) {
    Set<Integer> requestedIds = new HashSet<>();
    List<Pyramid> requestedPyramids = new ArrayList<>();

    while (true) {
      System.out.print("Enter Pyramid ID (or 'q' to stop): ");
      String input = scan.nextLine();

      if (input.equals("q")) {
        break;
      }

      int id = Integer.parseInt(input);
      if (!requestedIds.contains(id)) {
        requestedIds.add(id);
        for (Pyramid p : pyramidArray) {
          if (p.id == id) {
            requestedPyramids.add(p);
            break;
          }
        }
      }
    }

    // Print all unique requested pyramids
    for (Pyramid p : requestedPyramids) {
      System.out.printf("Pyramid %s\n", p.name);
      System.out.printf("\tid: %d\n", p.id);
      System.out.println("\tContributors:");
      for (String contributor : p.contributors) {
        System.out.printf("\t\t- %s\n", contributor);
      }
    }
  }

  // execute commands based on user input
  private Boolean executeCommand(Scanner scan, Character command) {
    Boolean success = true;

    switch (command) {
      case '1':
        printAllPharaoh();
        break;
      case '2':
        printPharaohById(scan);
        break;
      case '3':
        printAllPyramids();
        break;
      case '4':
        printPyramidById(scan);
        break;
      case '5':
        printUniquePyramids(scan);
        break;
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        System.out.println("ERROR: Unknown command");
        success = false;
    }

    return success;
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t\t%s\n", command, desc);
  }

  private static void printMenuLine() {
    System.out.println("--------------------------------------------------------------------------");
  }

  // prints the menu
  public static void printMenu() {
    printMenuLine();
    System.out.println("Nassef's Egyptian Pyramids App");
    printMenuLine();
    System.out.printf("Command\t\tDescription\n");
    System.out.printf("-------\t\t---------------------------------------\n");
    printMenuCommand('1', "List all the pharaohs");
    printMenuCommand('2', "Display information for a specific pharaoh");
    printMenuCommand('3', "List all the pyramids and contributors");
    printMenuCommand('4', "Display information for a specific pyramid");
    printMenuCommand('5', "Reports requested pyramids without duplicates");
    printMenuCommand('q', "Quit");
    printMenuLine();
  }
}
