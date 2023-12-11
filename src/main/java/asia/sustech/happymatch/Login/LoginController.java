package asia.sustech.happymatch.Login;

import asia.sustech.happymatch.NetUtils.HttpRequest;
import asia.sustech.happymatch.NetUtils.HttpRequests;
import asia.sustech.happymatch.NetUtils.HttpResult;
import asia.sustech.happymatch.Utils.FormatValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController {
    private double oldStageX;
    private double oldStageY;
    private double oldScreenX;
    private double oldScreenY;
    public ImageView register_img;
    public ImageView login_img;
    public Text clear;
    @FXML
    private PasswordField passWord;
    @FXML
    private TextField userName;
    @FXML
    private ImageView close_window;
    @FXML
    private Button register_bt;
    @FXML
    private Button login_bt;
    @FXML
    private Text forget_pwd;

    //清空文本
    @FXML
    void setClear(MouseEvent event) {
        userName.setText("");
        passWord.setText("");
    }
    //退出游戏
    @FXML
    void setClose_window(MouseEvent event){
        String info = "是否退出？";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES), new ButtonType("取消", ButtonBar.ButtonData.NO));
        alert.setHeaderText(null);
        alert.setTitle("提示");
        alert.showAndWait();
        if (alert.getResult().getButtonData().equals(ButtonBar.ButtonData.YES))
            System.exit(0);
    }
    //登录按钮按下
    @FXML
    void setLogin_bt_pressed(MouseEvent event) {
        //图片框透明度变化
        login_img.setOpacity(0.7);
    }
    //登录按钮松开
    @FXML
    void  setLogin_bt_released(MouseEvent event){
        //图片框透明度变化
        login_img.setOpacity(1);
        String username = userName.getText();
        String password = passWord.getText();
        if ((FormatValidator.isUserNameInvalid(username)&& FormatValidator.isEmailInvalid(username)) ||
                FormatValidator.isPasswordInvalid(password)) {
            String info = "用户名或密码格式不正确！";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("提示");
            alert.showAndWait();
        } else {
            //登录
            Thread thread = new Thread(() -> {
                //发起登录请求
                Platform.runLater(() -> {
                    HttpResult result = HttpRequests.login(username, password);
                    String info;
                    switch (result.getCode()){
                        case 200:
                            info = "登录成功！";
                            break;
                        case 400:
                            info = "登录失败，用户名或密码错误";
                            break;
                        default:
                            info = "内部错误";
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定",
                            ButtonBar.ButtonData.YES));
                    alert.setHeaderText(null);
                    alert.setTitle("提示");
                    alert.showAndWait();
                });
            });

            thread.start();
        }
    }
    //注册按钮按下
    @FXML
    void setRegister_bt_pressed(MouseEvent event) {
        //图片框透明度变化
        register_img.setOpacity(0.7);
    }
    //注册按钮松开
    @FXML
    void setRegister_bt_released(MouseEvent event) throws IOException {
        //图片框透明度变化
        register_img.setOpacity(1);
        //跳转注册页面
        Stage primaryStage = (Stage) register_bt.getScene().getWindow();
        //加载fxml文件
        URL url = getClass().getResource("/RegisterStage.fxml");
        //加载完fxml文件后，获取其中的root
        Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        //设置场景
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
    }

    @FXML
    void setOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            //询问是否退出
            String info = "是否退出？";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES), new ButtonType("取消", ButtonBar.ButtonData.NO));
            alert.setHeaderText(null);
            alert.setTitle("提示");
            alert.showAndWait();
            if (alert.getResult().getButtonData().equals(ButtonBar.ButtonData.YES))
                System.exit(0);
        }
    }
    @FXML
    void setOnMousePressed(MouseEvent event) {
        Stage primaryStage = (Stage) close_window.getScene().getWindow();

        oldStageX = primaryStage.getX();
        oldStageY = primaryStage.getY();
        oldScreenX = event.getScreenX();
        oldScreenY = event.getScreenY();
    }
    @FXML
    void setOnMouseDrag(MouseEvent event) {
        Stage primaryStage = (Stage) close_window.getScene().getWindow();
        primaryStage.setX(event.getScreenX() - oldScreenX + oldStageX);
        primaryStage.setY(event.getScreenY() - oldScreenY + oldStageY);
    }
}