import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HomeserviceService } from '../homeservice.service';

@Component({
  selector: 'app-forgotpass',
  templateUrl: './forgotpass.component.html',
  styleUrls: ['./forgotpass.component.css']
})
export class ForgotpassComponent implements OnInit {
  public k=0;
  public m=0;
  public n=0;
  public email="";
  public otp="";
  public backOtp="";
  public span1=0;
  public span2=0;
  public prevPass="";
  public newPass="";
  public complete=0;
  public super=1;
  constructor(private homeservice:HomeserviceService, private route:ActivatedRoute, private router:Router) { }

  ngOnInit(): void {
  }
GetOtp(){
  const data = {
    "emailId":this.email
  };
  this.homeservice.sendOtp(data).subscribe(ans =>{
    console.log(ans);
    if(this.email=="" || ans=="Not An User"){
         this.span1=1;
    }
    else{
      this.backOtp=ans;
      this.n=1;
    this.k=1;
    }
    
  });
}
sendOtp(){
  console.log(this.otp)
    console.log(this.backOtp)
    const data={
      "emailId":this.email,
      "otp":this.otp,
      "password":""
    }
    this.homeservice.CheckOtp(data).subscribe(ans =>{
      console.log(ans)
      if(ans=="Yes"){
        
    this.m=1;
    this.n=0;
      }
      else{
        this.span2=1;
      }
      
    });
}
SaveNewPass(){
if(this.prevPass==this.newPass){
  const data={
    "emailId":this.email,
    "otp":"",
    "password":this.newPass
  }
  this.homeservice.SaveNewPassword(data).subscribe(ans =>{
    console.log(ans);
    if(ans=="Password updated Successfully"){
      console.log("Validated");
      this.complete=1;
      this.super=0;
    }
  });

}
 
}
GoToLogin(){
  console.log("Validated");
  
  this.router.navigate(['/login']);
}

}
