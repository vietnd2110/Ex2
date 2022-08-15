package Ex2;

import java.util.Random;

public class TrietGia extends PhongAnCua5TrietGia implements Runnable {
    // Cac bien cua cac  triet gia
    private String ten;
    private TrangThai trangThai;
    private int id;
    public int lanAnThu = 0;

    private Dua duaPhai;
    private Dua duaTrai;
    Random rd = new Random();

    /****************/

    public TrietGia(int ID, String ten, TrangThai trangThai) {
        super();
        setId(ID);
        setTen(ten);
        setTrangThai(trangThai);
        setCacDuaXungQuanhTrietGia();
    }

    /****************/

    public void run() {
        while (true) {
            suyNghi(); // trạng thái mạc định là suy nghĩ
            if (layDua()) { // nếu lấy đũa thì ăn
                an();
                datDuaXuong(); // ăn xong đặt đũa xuống
            }
        }
    }

    /**
     * Phuong thuc cho viec su dung dua
     * Kiem tra co lay ra hay khong
     * @return boolean
     */
    private synchronized boolean layDua() {
        setTrangThai(TrangThai.DOIBUNG); // nếu muốn lấy đũa trước hết phải thông báo cho người phuc vụ semaphore biết trạng thái đang đói

        /*
         * Triết gia xin phép người phục vụ semaphore để lấy đã
         *Người phục vụ semaphore cố gắng cung cấp tài nguyên
         *Nếu số người truy cập vào tài nguyên nhỏ hơn 2 thì sẽ cho phép
         *Không thì sẽ không lấy đũa được. Phương thức này sẽ trả về false
         */


        while (getTrangThai() == TrangThai.DOIBUNG) { //nếu trạng thái vẫn đói bụng
            if (getTrangThai() == TrangThai.DOIBUNG && duaPhai.isCoSanKhong() && duaTrai.isCoSanKhong()) { // lên tục kiểm tra dòm ngó
                // bên trái và bên phải đều có thì cầm thêm.

                duaPhai.setCoSanKhong(false);// khi cầm lên rồi thay đổi giá trị rằng đũa trái, phải ko có sẵn
                duaTrai.setCoSanKhong(false);

                System.out.println(ten + " da cam dua trai " + duaTrai.getId() + " va dua phải "
                        + duaPhai.getId());
                try {
                    nguoiPhucVu.acquire(); // cho phep truy cap tai nguyen. Giảm số người truy cập vào tài nguyên 1 đơn vị người
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }

    // Phương thức biểu hiện cho việc đặt đũa xuống
    private synchronized void datDuaXuong() {
        duaPhai.setCoSanKhong(true);// khi bỏ xuống rồi thay đổi giá trị rằng đũa trái, phải là có sẵn
        duaTrai.setCoSanKhong(true);

        /*
         * Triết gia  nói người phục vụ (semaphore) rằng anh ấy đã ăn xong
         */
        nguoiPhucVu.release();  //Tăng số người truy cập vào tài nguyên lên 1 đơn vị người

        System.out.println(
                ten + " dat dua " + duaPhai.getId() + " va dua " + duaTrai.getId()+" xuong");
    }

    /**
     * Phương thức biểu hiện rằng triết gia đang suy nghĩ
     * Thời gian suy nghĩ là ngẫu nhiên
     */
    private synchronized void suyNghi() {
        setTrangThai(TrangThai.DANGSUYNGHI);

        waitrnd();
    }

    /**
     *Phương thức biểu hiện rằng tôi đang ăn.
     *Khoảng thời gian ăn sẽ là ngẫu nhiên
     */
    private synchronized void an() {
        lanAnThu++; // tăng số lần ăn lên 1
        setTrangThai(TrangThai.DANGAN); // thay đổi trạng thái là đang ăn
        waitrnd(); // ăn 1 khoảng thời gian ngẫu nhiên
    }

    /**
     * Phương thức biểu hiện đợi 1 khoảng thời gian ngẫu nhiên
     * (= giữa 0.1 và 5 giây)
     */
    private void waitrnd() {
        try {
            this.wait((rd.nextInt(50) + 1) * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return ten
     */
    public String getTen() {
        return ten;
    }

    /**
     * @param ten
     *            the name to set
     */
    public void setTen(String ten) {
        this.ten = ten;
    }

    /**
     * @return trang thai
     */
    public TrangThai getTrangThai() {
        return trangThai;
    }

    /**
     * @param trangThai
     *           thay đổi thành 1 trạng thái khác
     */
    public void setTrangThai(TrangThai trangThai) {
        this.trangThai = trangThai;
        if (getTrangThai() == TrangThai.DANGAN)
            System.out.println(this.ten + " dang an " + this.trangThai.toString() + " lan thu: " + lanAnThu + "");
        else
            System.out.println(this.ten  + this.trangThai.toString());
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            thay đổi thành 1 id khác
     */
    public void setId(int id) {
        this.id = id;
    }
    // sử dụng id của triết gia. có 5 người id được khởi tạo từ 1 -> 5. Được khởi tạo tại class PhongAnCua5TrietGia
    private void setCacDuaXungQuanhTrietGia() {
        duaPhai = danhSachCacChiecDua.get((id%5));// tại sao lại chia dư cho 5? danh sách có
        // index từ 0 đến 4 đối với 1 danh sách có độ dài là 5
        // Nếu id của đũa là 1 thì % 5 sẽ là 1. Không có gì thay đổi. Nhưng
        // nếu id =5 thì sao.
        // List ds=  new ArrayList(5);
        // ds.get(5); //erro
        // vì vậy nếu id = 5 khi chia dư cho 5 sẽ thành 0. biểu hiện rằng triết gia thứ 5 id = 5 có thể lấy đũa thứ
        // 0 (5) và đũa thức nhất. Cái bàn hình tròn khép kín
        duaTrai = danhSachCacChiecDua.get((id - 1));
        // tại sao lại id - 1
        // ví dụ người thứ 3 thì bên phải sẽ có đũa thứ 3%5=3 và đũa phải là đũa thứ sẽ là thức 2 = 3-1(3=id)
    }
}
