package action;

import java.util.Date;

import model.User;
import service.UserService;

public class ChangePasswordAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String validatecode;
	private int userid;
	
	private UserService userService;
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String validatecode(){
		return validatecode;
	}
	
	public void setValidatecode(String validatecode){
		this.validatecode = validatecode;
	}
	
	public int getUserid(){
		return userid;
	}
	
	public void setUserid(int userid){
		this.userid = userid;
	}
	
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	
	@Override
	public String execute() throws Exception{
		session.put("message", "");
		User user = userService.getUserByUserid(userid);
		if(user != null){
			Date currentTime = new Date();
			currentTime.before(user.getEmailregistertime());
			if(currentTime.before(user.getLastActivateTime())){
				if(validatecode.equals(user.getValidatecode())){
					session.put("message", "您的邮箱已成功激活");
					return SUCCESS;
				}
				else{
					session.put("message", "激活码不正确");
				}
			}
			else{
				session.put("message", "激活码已过期");
			}
		}
		else{
			session.put("message", "该用户不存在");
		}
		return ERROR;
	}
	
}