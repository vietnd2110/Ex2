package Ex2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class PhongAnCua5TrietGia {
    static List<TrietGia> danhSachCacNhaTrietGia = new ArrayList<TrietGia>();
    static List<Dua> danhSachCacChiecDua = new ArrayList<Dua>();
    static List<Thread> danhSachCacTienTrinh = new ArrayList<Thread>();

    // Tên các triết gia được thể hiện ra console
    static String[] tenTrietGia = { "Triet gia Phong ", "Triet gia Hau ", "Triet gia Nga ",
            "Triet gia Thinh ", "Triet gia Anh " };

    // Giá trị true để đảm bảo rằng 1 triết gia đang sử dụng tài nguyên sẽ không sử dụng được nữa cho tới khi bỏ đũa xuống
    Semaphore nguoiPhucVu = new Semaphore(2, true); // nguoi phuc vu cua cac triet gia. Chỉ cho phép 2 triết gia truy cập tài nguyên
    // 1 phép tính đơn giản 2 người ăn 4 đũa còn dư 1 đũa sao có người thứ 3 ăn được. Nên chỉ cho 2 người truy cập vào tài nguyên đũa

    // Các hằng số
    static final int SONHATRIETGIA = 5;
    static final int SOCHIECDUA = 5;

    /****************/

    public static void main(String[] args) {
        //Khỏi tạo danh sách các chiếc đũa
        for (int i = 1; i <= SOCHIECDUA; i++) {
            danhSachCacChiecDua.add(new Dua(i, true));
        }
        // Khởi tạo các tiến trình
        for (int i = 0; i < SONHATRIETGIA; i++) {
            danhSachCacNhaTrietGia.add(new TrietGia((i + 1), tenTrietGia[i], TrangThai.DANGSUYNGHI));
            danhSachCacTienTrinh.add(new Thread(danhSachCacNhaTrietGia.get(i)));
        }

        // Khởi động các tiến trình
        for (int i = 0; i < danhSachCacTienTrinh.size(); i++) {
            danhSachCacTienTrinh.get(i).start();
        }
    }
}
