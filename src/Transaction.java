import java.time.LocalDate;

/** 家計簿の収入または支出1件を表します。 */
public class Transaction {
    private final LocalDate date;
    private final String type;
    private final String category;
    private final long amount;
    private final String memo;

    public Transaction(LocalDate date, String type, String category, long amount, String memo) {
        if (date == null) {
            throw new IllegalArgumentException("日付が正しくありません。");
        }
        if (!"収入".equals(type) && !"支出".equals(type)) {
            throw new IllegalArgumentException("種別を選んでください。");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("カテゴリを選んでください。");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("金額は1円以上を入力してください。");
        }

        this.date = date;
        this.type = type;
        this.category = category.trim();
        this.amount = amount;
        this.memo = memo == null ? "" : memo.trim();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public long getAmount() {
        return amount;
    }

    public String getMemo() {
        return memo;
    }
}

