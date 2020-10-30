import com.vndirect.atm.userinterface.consoleviewImpl.ConsoleViewImpl;
import com.vndirect.atm.userinterface.View;

public class ATM {
    public static void main(String[] args) {
        View view = new ConsoleViewImpl();
        view.insertCard();
    }
}
