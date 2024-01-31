package libary;

import javax.swing.*;

public class ExecptionHandler {

    public ExecptionHandler(Exception e) {
        super();
        e.printStackTrace();
        Object[] options = {"OK", "Show Error-Log"};
        int optionChoosen = JOptionPane.showOptionDialog(null, e.getLocalizedMessage(), "Exception-Hanlder", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
        if (optionChoosen == 1) {
            JOptionPane.showMessageDialog(null, stackTraceToString(e));
        }
    }

    private static String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void handleException(Exception e) {
        e.printStackTrace();
    }

    public static void handleExceptionGUI(Exception e) {
        new ExecptionHandler(e);
    }

    public static void main(String[] args) {
        new ExecptionHandler(new Exception("Hoila Leit"));
    }
}
