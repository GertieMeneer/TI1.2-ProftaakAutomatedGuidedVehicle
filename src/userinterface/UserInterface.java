package userinterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class UserInterface extends Application {
    private ArrayList<String> tableCoords = new ArrayList<String>();
    private ArrayList<CheckBox> tableCheckbox = new ArrayList<>();
    private ArrayList<String> kitchenCoords = new ArrayList<>();
    private ArrayList<String> cartCoords = new ArrayList<>();
    private ArrayList<CheckBox> allCheckBoxes = new ArrayList<>();
    private String selection;
    private Navigation bob = new Navigation();
    private String action;
    private boolean rotation;
    private String finalroute = "";

    public UserInterface() {

    }

    public static void main(String[] args) {
        launch(UserInterface.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //making components for general ui
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(550, 350);

        VBox selectTableVBox = new VBox();
        selectTableVBox.setSpacing(15);

        VBox selectAction = new VBox();
        selectAction.setSpacing(10);

        ToggleGroup action = new ToggleGroup();
        RadioButton take = new RadioButton("Ophalen");
        RadioButton drop = new RadioButton("Neerzetten");
        RadioButton drive = new RadioButton("Rijden");
        action.selectedToggleProperty();

        take.setToggleGroup(action);
        drop.setToggleGroup(action);
        drive.setToggleGroup(action);
        selectAction.getChildren().addAll(take, drop, drive);

        take.setOnAction(e -> {     //actions when radiobutton is pressed and save it as string
            this.action = "get";
            this.rotation = true;
        });

        drop.setOnAction(e -> {
            this.action = "put";
            this.rotation = true;
        });

        drive.setOnAction(e -> {
            this.action = "drive";
            this.rotation = false;
        });

        GridPane checkboxGrid = new GridPane();
        GridPane checkBoxGridCart = new GridPane();
        GridPane buttonGrid = new GridPane();

        buttonGrid.setVgap(10);
        buttonGrid.setHgap(10);

        checkboxGrid.setHgap(10);
        checkboxGrid.setVgap(10);

        checkBoxGridCart.setHgap(10);
        checkBoxGridCart.setVgap(10);

        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);

        MenuBar menuBar = new MenuBar();
        Menu actionMenu = new Menu("Acties");
        Menu statusMenu = new Menu("Status");

        menuBar.getMenus().addAll(actionMenu, statusMenu);

        MenuItem mainMenu = new MenuItem("Hoofdmenu");
        MenuItem selectTable = new MenuItem("Tafel positie");
        MenuItem selectCart = new MenuItem("Kar positie");
        MenuItem selectKitchen = new MenuItem("Keuken positie");

        if(bob.getStatus()) {       //get boebot bluetooth status to show in gui
            statusMenu.setText("Status: verbonden");
        } else {
            statusMenu.setText("Status: niet verbonden");
        }

        actionMenu.getItems().addAll(mainMenu, selectTable, selectCart, selectKitchen);

        selectTableVBox.getChildren().add(checkboxGrid);

        makeCheckGridPane(checkboxGrid, 8, 6);
        makeCheckGridPaneCart(checkBoxGridCart, 8, 5);
        makeGridPane(buttonGrid, 8, 6);

        Label welcome = new Label("Welkom!");

        borderPane.setTop(menuBar);
        borderPane.setCenter(welcome);

        mainMenu.setOnAction(e -> {             //set properties of menu items when one is pressed
            mainMenu.setDisable(true);
            selectKitchen.setDisable(false);
            selectCart.setDisable(false);
            selectTable.setDisable(false);
            System.out.println("Hoofdmenu");
            borderPane.setCenter(buttonGrid);
            borderPane.setBottom(selectAction);
        });

        selectTable.setOnAction(e -> {
            System.out.println("Selecteer tafel");
            this.selection = "table";
            clearCheckBoxes();
            mainMenu.setDisable(false);
            selectKitchen.setDisable(false);
            selectCart.setDisable(false);
            selectTable.setDisable(true);

            borderPane.setCenter(checkboxGrid);
        });

        selectCart.setOnAction(e -> {
            System.out.println("Selecteer kar");
            this.selection = "cart";
            clearCheckBoxes();
            mainMenu.setDisable(false);
            selectKitchen.setDisable(false);
            selectCart.setDisable(true);
            selectTable.setDisable(false);

            borderPane.setCenter(checkBoxGridCart);
        });

        selectKitchen.setOnAction(e -> {
            System.out.println("Selecteer keuken");
            this.selection = "kitchen";
            clearCheckBoxes();
            mainMenu.setDisable(false);
            selectKitchen.setDisable(true);
            selectCart.setDisable(false);
            selectTable.setDisable(false);

            borderPane.setCenter(checkboxGrid);
        });

        Scene Scene = new Scene(borderPane);

        primaryStage.setScene(Scene);
        primaryStage.setTitle("BoeBot GUI");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private ArrayList<String> makeCheckGridPaneCart(GridPane gridPane, int x, int y) {      //method to make checkbox grid for cart selection
        for (int i = 1; i < y; i++) {
            for (int j = 1; j < x; j++) {
                CheckBox checkBox = new CheckBox(j + ", " + i);
                checkBox.setMaxSize(50, 50);

                checkBox.getProperties().put('x', j);
                checkBox.getProperties().put('y', i);
                allCheckBoxes.add(checkBox);

                Button submit = new Button("Opslaan");
                gridPane.add(submit, x - x, y + 1);
                submit.setOnAction(e -> {
                    if (this.selection.equals("cart")) {
                        this.cartCoords.clear();
                        for (CheckBox checkBox1 : allCheckBoxes) {
                            if (checkBox1.isSelected()) {
                                int xCoord = (int) checkBox1.getProperties().get('x');
                                int yCoord = (int) checkBox1.getProperties().get('y');
                                this.cartCoords.add(xCoord + "" + yCoord);
                            }
                        }
                    }
                    System.out.println(this.cartCoords);
                });
                gridPane.add(checkBox, j, i);
                allCheckBoxes.add(checkBox);
            }
        }
        return tableCoords;
    }


    private ArrayList<String> makeCheckGridPane(GridPane gridPane, int x, int y) {      //method to make checkbox grid to select kitchen
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                CheckBox checkBox = new CheckBox(j + ", " + i);
                checkBox.setMaxSize(50, 50);

                checkBox.getProperties().put('x', j);
                checkBox.getProperties().put('y', i);
                allCheckBoxes.add(checkBox);

                Button submit = new Button("Opslaan");
                gridPane.add(submit, x - x, y + 1);
                submit.setOnAction(e -> {
                    if (this.selection.equals("table")) {                               //see which action is selected and save selected checkboxes to specific arraylist with x and y properties
                        this.tableCoords.clear();
                        for (CheckBox checkBox1 : allCheckBoxes) {
                            if (checkBox1.isSelected()) {
                                int xCoord = (int) checkBox1.getProperties().get('x');
                                int yCoord = (int) checkBox1.getProperties().get('y');
                                this.tableCoords.add(xCoord + "," + yCoord);
                                this.tableCheckbox.add(checkBox1);
                            }
                        }
                    }
                    if (this.selection.equals("cart")) {
                        this.cartCoords.clear();
                        for (CheckBox checkBox1 : allCheckBoxes) {
                            if (checkBox1.isSelected()) {
                                int xCoord = (int) checkBox1.getProperties().get('x');
                                int yCoord = (int) checkBox1.getProperties().get('y');
                                this.cartCoords.add(xCoord + "," + yCoord);
                            }
                        }
                    }
                    if (this.selection.equals("kitchen")) {
                        this.kitchenCoords.clear();
                        for (CheckBox checkBox1 : allCheckBoxes) {
                            if (checkBox1.isSelected()) {
                                int xCoord = (int) checkBox1.getProperties().get('x');
                                int yCoord = (int) checkBox1.getProperties().get('y');
                                this.kitchenCoords.add(xCoord + "," + yCoord);
                            }
                        }
                    }
                    System.out.println(this.tableCoords);
                    System.out.println(this.cartCoords);
                    System.out.println(this.kitchenCoords);
                });
                gridPane.add(checkBox, j, i);
                allCheckBoxes.add(checkBox);
            }
        }
        for (String pos : this.cartCoords) {             //save cart positions as blockades in arraylist
            this.tableCoords.add(pos);
        }
        bob.copyTableCoords(this.tableCoords);
        bob.copyKitchenCoords(this.kitchenCoords);
        return tableCoords;
    }

    private void makeGridPane(GridPane gridPane, int x, int y) {            //method to make button grid in hoofdmenu
        ArrayList<Button> btnList = new ArrayList<>();

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Button button = new Button(j + ", " + i);
                button.setMaxSize(50, 50);

                button.getProperties().put('x', j);
                button.getProperties().put('y', i);
                button.setOnAction(e -> {                           //when button is pressed: look at properties and selected action and start calculation of route and specific action
                    Button b = (Button) e.getSource();
                    int xb = (int) b.getProperties().get('x');
                    int yb = (int) b.getProperties().get('y');
                    if(this.action.equals("get")) {
                        bob.setDestinationRotation(3);
                        finalroute += bob.calculate(xb+1, yb, rotation, action);
                        finalroute += bob.calculate(1, 0, rotation, "put");
                    } else if(this.action.equals("put")) {
                        bob.setDestinationRotation(3);
                        finalroute += bob.calculate(xb-1, yb, rotation, action);
                        bob.setDestinationRotation(0);
                        finalroute += bob.calculate(0, 0, rotation, "drive");
                    } else {
                        bob.calculate(xb, yb, rotation, action);
                    }
                    System.out.printf("%d, %d\n", xb, yb);
                });
                gridPane.add(button, j, i);
                btnList.add(button);
            }
        }
    }

    public void clearCheckBoxes() {             //clear checkboxes in menu
        for (CheckBox checkBox : this.allCheckBoxes) {
            checkBox.setSelected(false);
        }
    }
}

