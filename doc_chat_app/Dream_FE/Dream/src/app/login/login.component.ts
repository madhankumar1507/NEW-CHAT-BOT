import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HomeserviceService } from '../homeservice.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public username="";
  public password="";
  public evaluator="";
  public k=0;
  constructor(private homeservice:HomeserviceService, private route: ActivatedRoute, private router:Router) { }

  ngOnInit(): void {
  }
Onclick(){
  const data = {
    "password" :this.password,
    "emailId" : this.username
  };
  console.log(data);
   this.homeservice.login(data).subscribe(ans =>{
    console.log(ans)
    this.evaluator=ans;
    console.log(this.evaluator);
    if(this.evaluator=="Yes"){
      this.router.navigate(['customerhome']);
    }
    else{
      this.k=1;
    }
    
  });
}
  
}
