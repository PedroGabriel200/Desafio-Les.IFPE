package Repository;

import Model.Sell;
import java.io.IOException;
import java.util.List;

public class SellRepository extends BinaryFileRepository<Sell> {
    public SellRepository() { super("sells.bin"); }

    public List<Sell> findAll() throws IOException { return readAll(); }
    public void save(Sell sell) throws IOException {
        List<Sell> sells = readAll();
        sells.add(sell);
        writeAll(sells);
    }
    public boolean updateAt(int index, Sell sell) throws IOException {
        List<Sell> sells = readAll();
        if (index < 0 || index >= sells.size()) return false;
        sells.set(index, sell);
        writeAll(sells);
        return true;
    }
    public boolean deleteAt(int index) throws IOException {
        List<Sell> sells = readAll();
        if (index < 0 || index >= sells.size()) return false;
        sells.remove(index);
        writeAll(sells);
        return true;
    }
}
