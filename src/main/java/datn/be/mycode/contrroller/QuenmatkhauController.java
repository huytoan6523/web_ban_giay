package datn.be.mycode.contrroller;

import datn.be.mycode.entity.KhachHang;
import datn.be.mycode.request.DangKy.CheckOtpReq;
import datn.be.mycode.request.DangKy.DangKy;
import datn.be.mycode.request.KhachHangRequest;
import datn.be.mycode.service.DangkySevice;
import datn.be.mycode.service.KhachHangService;
import datn.be.mycode.service.QuenMatKhauSevice;
import datn.be.mycode.util.EmailSender;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quen-mat-khau")
public class QuenmatkhauController {
    @Autowired
    private QuenMatKhauSevice sevice;

    @Autowired
    private KhachHangService Sv;

    @Autowired
    EmailSender emailSender;
    @GetMapping("/gmail")
    public String show(@RequestParam(name = "email",required = false) String email,Model model) {
//

        return "otp1"; // Điều hướng tới trang
    }
    @GetMapping("/hien-thi")
    public String showForm(Model model, @RequestParam(name = "email",required = false) String email) {

        model.addAttribute("user", new KhachHangRequest());
        model.addAttribute("email", email);
        return "quenmatkhau"; // Điều hướng tới trang
    }
    @PostMapping("/kiem-tra-email")
    public String checkEmail(
            @ModelAttribute("email") String email,
            RedirectAttributes redirectAttributes,
            Model model){

        emailSender.sendEmailOtp(email);
        redirectAttributes.addAttribute("email",email);
        return "redirect:/quen-mat-khau/view-opt";

    }
    @GetMapping("/view-opt")
    public String checkOTP(
            @RequestParam(name = "email",required = false) String email,
            @RequestParam(name = "erro",required = false) String erro,
            Model model){
        if (erro != null) model.addAttribute("erro3", "otp khong dung");
        model.addAttribute("email", email);
        return "checkotp1";

    }
    @PostMapping("/kiem-tra-opt")
    public String checkOTP(
            @ModelAttribute() CheckOtpReq req,
            RedirectAttributes redirectAttributes,
            Model model){

        boolean isValid =  emailSender.checkOtp(req.getEmail(),req.getOtp());
        if (isValid) {
            redirectAttributes.addAttribute("email",req.getEmail());
            return "redirect:/quen-mat-khau/hien-thi";
        }
//        model.addAttribute("erro3", "otp khong dung");
        return "redirect:/quen-mat-khau/view-opt?erro=otp khong dung";

    }

    @PostMapping("/success1")
    public String processRegistration(
            @Valid @ModelAttribute("user") KhachHangRequest khachhang,
            BindingResult bindingResult,
            @RequestParam String matKhau,
            @RequestParam(name = "email",required = false) String email,
            Model model,
            RedirectAttributes redirectAttribute
    ) {
        if (!khachhang.getMatKhau().equals(khachhang.getConfirmPassword())) {
            model.addAttribute("erro2", "Mat khau phai trung!");
            model.addAttribute("email",khachhang.getEmail());
            return "quenmatkhau"; }

        if (bindingResult.hasErrors()) {
            model.addAttribute("email", khachhang.getEmail());
            return "quenmatkhau"; // Quay lại trang đăng ký nếu có lỗi
        }


        // Lưu thông tin người dùng
        Sv.updateByEmail(email,matKhau);

        model.addAttribute("successMessage", "Đăng ký thành công!");
        return "success1"; // Điều hướng tới trang success.jsp
    }

}
