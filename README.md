# TCP-IP-RMI_Java_GameDuaXe
Tuần này, thầy yêu cầu tôi làm về Game đua xe qua TCP/IP kết hợp RMI, tôi đã làm project này dựa trên mẫu của thầy.

Yêu cầu:
  Kết hợp TCP và RMI trong ứng dụng của BTL:
  - Hệ thống có 3 tầng: tầng 1 là client TCP, tầng 2 là server TCP đồng thời làm clientcủa RMI, tầng 3 là server RMI
  - Server RMI quản lí các chức năng liên quan đến CSDL (đăng kí, đăng nhập, bạn bè, hội nhóm, thách đấu, kết quả, xếp hạng), nên các lớp DAO nằm ở tầng server RMI
  - Server TCP quản lí các chức năng cần đồng bộ như thi đấu, gửi nhận message đồng bộ...
  - Client TCP sẽ giao tiếp trực tiếp với người chơi.
  - Các yêu cầu từ client TCP đều gửi lên server TCP.
  - Tại server TCP, những chức năng đồng bộ thì tự thực hiện ngay cho các client. Những chức năng liên quan lưu dữ liệu vào hay đọc dữ liệu ra thì nó phải gửi sang server RMI để thực hiện. Khi đó, server TCP thành client RMI của server RMI.

Các chức năng chính:

    Đăng nhập (người chơi đăng nhập để chơi)
    Đăng ký (tạo thêm người chơi mới)
    Kết bạn từ danh sách xếp hạng người chơi (kết bạn giữa 2 người)
    Hủy kết bạn
    Xem danh sách bạn bè
    Hiển thị người chơi online
    Xem bảng xếp hạng (có hiện online/offline)
    Tạo nhóm mới, xin vào nhóm đã có, rời nhóm
    ...
    (Tất cả đều đồng bộ thông qua Server)

Hiện tôi đang xây dựng thêm các chức năng cho hoàn thiện hơn. Đây chỉ là project tham khảo, mọi người góp ý. Xin cảm ơn!!!
