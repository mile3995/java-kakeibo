import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** 収入と支出を記録し、一覧と合計を表示するコンソール家計簿です。 */
public class HouseholdBudgetApp {
    private final List<Transaction> transactions = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in, "UTF-8");

    public static void main(String[] args) {
        HouseholdBudgetApp app = new HouseholdBudgetApp();
        app.run();
    }

    private void run() {
        System.out.println("Java 家計簿へようこそ。");
        while (true) {
            showMenu();
            System.out.print("番号を選んでください: ");
            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                showTransactions();
            } else if ("2".equals(choice)) {
                addTransaction();
            } else if ("3".equals(choice)) {
                deleteTransaction();
            } else if ("4".equals(choice)) {
                showSummary();
            } else if ("0".equals(choice)) {
                System.out.println("終了します。");
                break;
            } else {
                System.out.println("0〜4の番号を入力してください。");
            }
        }
    }

    private void showMenu() {
        System.out.println();
        System.out.println("1. 記録一覧");
        System.out.println("2. 収入・支出を追加");
        System.out.println("3. 記録を削除");
        System.out.println("4. 合計を表示");
        System.out.println("0. 終了");
    }

    private void addTransaction() {
        System.out.println("1. 支出");
        System.out.println("2. 収入");
        System.out.print("種別を選んでください: ");
        String typeChoice = scanner.nextLine().trim();

        String type;
        if ("1".equals(typeChoice)) {
            type = "支出";
        } else if ("2".equals(typeChoice)) {
            type = "収入";
        } else {
            System.out.println("1か2を入力してください。");
            return;
        }

        System.out.print("カテゴリ: ");
        String category = scanner.nextLine().trim();
        System.out.print("金額（円、例: 1200）: ");
        String amountText = scanner.nextLine().trim();

        long amount;
        try {
            amount = Long.parseLong(amountText);
        } catch (NumberFormatException e) {
            System.out.println("金額は半角数字で入力してください。");
            return;
        }

        System.out.print("メモ（省略可）: ");
        String memo = scanner.nextLine();

        try {
            Transaction transaction = new Transaction(
                    LocalDate.now(), type, category, amount, memo);
            transactions.add(transaction);
            System.out.println("記録を追加しました。");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("記録はまだありません。");
            return;
        }

        System.out.println("記録一覧:");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            System.out.println((i + 1) + ". "
                    + transaction.getDate() + " | "
                    + transaction.getType() + " | "
                    + transaction.getCategory() + " | "
                    + String.format("%,d円", transaction.getAmount()) + " | "
                    + transaction.getMemo());
        }
    }

    private void deleteTransaction() {
        showTransactions();
        if (transactions.isEmpty()) {
            return;
        }

        System.out.print("削除する番号: ");
        String numberText = scanner.nextLine().trim();
        int number;
        try {
            number = Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            System.out.println("番号を半角数字で入力してください。");
            return;
        }

        if (number < 1 || number > transactions.size()) {
            System.out.println("その番号の記録はありません。");
            return;
        }

        transactions.remove(number - 1);
        System.out.println("記録を削除しました。");
    }

    private void showSummary() {
        long income = 0;
        long expense = 0;
        for (Transaction transaction : transactions) {
            if ("収入".equals(transaction.getType())) {
                income += transaction.getAmount();
            } else {
                expense += transaction.getAmount();
            }
        }

        System.out.println("収入合計: " + String.format("%,d円", income));
        System.out.println("支出合計: " + String.format("%,d円", expense));
        System.out.println("差額: " + String.format("%,d円", income - expense));
    }

}

