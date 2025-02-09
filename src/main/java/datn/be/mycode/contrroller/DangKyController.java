package datn.be.mycode.contrroller;

import datn.be.mycode.request.DangKy.CheckOtpReq;
import datn.be.mycode.request.DangKy.DangKy;
import datn.be.mycode.service.DangkySevice;
import datn.be.mycode.util.EmailSender;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.regex.Pattern;

@Controller
@RequestMapping("/dang-ky")
public class DangKyController {
@Autowired
 private DangkySevice dangkySevice;
    @Autowired
    private EmailSender emailSender;

    @GetMapping("/lay-ma-opt")
    public String getOTP(Model model,@ModelAttribute("user") DangKy dangKy) {
//       var optCode = 100000 + new Random().nextInt(999999);
//       String otp = String.valueOf(optCode);
//            emailSender.sendEmailOtp(email);
            return "otp";

    }


    @GetMapping("/view-opt")
    public String checkOTP(
            @RequestParam(name = "email",required = false) String email,
            @RequestParam(name = "erro",required = false) String erro,
            Model model){
if (erro != null) model.addAttribute("erro3", "otp khong dung");
        model.addAttribute("email", email);
        return "checkotp";

    }

@PostMapping("/kiem-tra-email")
public String checkEmail(
        @ModelAttribute("email") String email,
        RedirectAttributes redirectAttributes,
        Model model){
    if (dangkySevice.existsByEmail(email)) {
        model.addAttribute("erro", "Email đã tồn tại!");
        return "redirect:/dang-ky/lay-ma-opt";
    }
    emailSender.sendEmailOtp(email);
    redirectAttributes.addAttribute("email",email);
    return "redirect:/dang-ky/view-opt";

}

    @PostMapping("/kiem-tra-opt")
    public String checkOTP(
            @ModelAttribute() CheckOtpReq req,
            RedirectAttributes redirectAttributes,
            Model model){

        boolean isValid =  emailSender.checkOtp(req.getEmail(),req.getOtp());
        if (isValid) {
            redirectAttributes.addAttribute("email",req.getEmail());
            return "redirect:/dang-ky/hien-thi";
        }
//        model.addAttribute("erro3", "otp khong dung");
        return "redirect:/dang-ky/view-opt?erro=otp khong dung";

    }

    @GetMapping("/hien-thi")
    public String showForm(@RequestParam(name = "email",required = false) String email,Model model) {
        model.addAttribute("user", new DangKy());
        model.addAttribute("email", email);
        return "dangky"; // Điều hướng tới trang dangky.jsp
    }

    @PostMapping("/success")
    public String processRegistration(
            @Valid @ModelAttribute("user") DangKy dangKy,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttribute
            ) {
        String VIETNAM_PHONE_PATTERN = "^(0[1-9][0-9]{8})$";
        boolean checkSDT = Pattern.matches(VIETNAM_PHONE_PATTERN, dangKy.getSDT());
        if (dangKy.getTenNguoiDung()==null || dangKy.getTenNguoiDung().isBlank()){
            model.addAttribute("erro4", "Tên người dùng không được để trống!");
            model.addAttribute("email", dangKy.getEmail());
            return "dangky";
        }if (checkSDT == false){
            model.addAttribute("erro3", "Số điện thoại không đúng!");
            model.addAttribute("email", dangKy.getEmail());
            model.addAttribute("user", dangKy);
            return "dangky";
        }
        if (!dangKy.getMatKhau().equals(dangKy.getConfirmPassword())) {
            model.addAttribute("erro2", "Mat khau phai trung!");
            model.addAttribute("email", dangKy.getEmail());
            return "dangky"; }
        if (dangkySevice.existsByTaiKhoan(dangKy.getTaiKhoan())) {
            model.addAttribute("erro1", "Tài khoản đã tồn tại!");
            model.addAttribute("email", dangKy.getEmail());
            return "dangky";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("email", dangKy.getEmail());
            return "dangky"; // Quay lại trang đăng ký nếu có lỗi
        }


        // Kiểm tra email hoặc tài khoản đã tồn tại

        // Lưu thông tin người dùng
         dangkySevice.add(dangKy);

        model.addAttribute("successMessage", "Đăng ký thành công!");
        return "success"; // Điều hướng tới trang success.jsp
    }

}

