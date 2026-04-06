import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface MessageResponse {
  welcomeEnglish: string;
  welcomeFrench: string;
  timeET: string;
  timeMT: string;
  timeUTC: string;
}

interface RoomSearchResponse {
  content: Room[];
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  constructor(private httpClient: HttpClient) {}

  private readonly baseURL: string = '';
  private readonly messagesUrl: string = `${this.baseURL}/api/messages`;
  private readonly roomsUrl: string = `${this.baseURL}/room/reservation/v1`;

  public welcomeEnglish: string = '';
  public welcomeFrench: string = '';
  public timeET: string = '';
  public timeMT: string = '';
  public timeUTC: string = '';
  public messagesLoadError: string = '';
  roomsearch!: FormGroup;
  rooms!: Room[];
  request!:ReserveRoomRequest;
  currentCheckInVal: string = '';
  currentCheckOutVal: string = '';

    ngOnInit() {
      this.httpClient.get<MessageResponse>(this.messagesUrl)
        .subscribe({
          next: (data) => {
            this.welcomeEnglish = data.welcomeEnglish;
            this.welcomeFrench = data.welcomeFrench;
            this.timeET = data.timeET;
            this.timeMT = data.timeMT;
            this.timeUTC = data.timeUTC;
          },
          error: (err) => {
            console.error('Failed to load /api/messages', err);
            this.messagesLoadError = 'Failed to load messages from the server.';
          }
        });
      this.roomsearch = new FormGroup({
        checkin: new FormControl(''),
        checkout: new FormControl('')
      });

    const roomsearchValueChanges$ = this.roomsearch.valueChanges;

    // subscribe to the stream
    roomsearchValueChanges$.subscribe(x => {
      this.currentCheckInVal = x.checkin ?? '';
      this.currentCheckOutVal = x.checkout ?? '';
    });
  }

    onSubmit({value,valid}:{value:Roomsearch,valid:boolean}){
      this.getAll().subscribe(
        rooms => {
          this.rooms = rooms.content;
        }
      );
    }
    reserveRoom(value:string){
      this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);

      this.createReservation(this.request);
    }
    createReservation(body:ReserveRoomRequest) {
      this.httpClient.post(this.roomsUrl, body)
        .subscribe(res => console.log(res));
    }

    getAll(): Observable<RoomSearchResponse> {
       return this.httpClient.get<RoomSearchResponse>(
         `${this.roomsUrl}?checkin=${encodeURIComponent(this.currentCheckInVal)}&checkout=${encodeURIComponent(this.currentCheckOutVal)}`
       );
    }

  }



export interface Roomsearch{
    checkin:string;
    checkout:string;
  }




export interface Room{
  id:string;
  roomNumber:string;
  price:string;
  links:string;

}
export class ReserveRoomRequest {
  roomId:string;
  checkin:string;
  checkout:string;

  constructor(roomId:string,
              checkin:string,
              checkout:string) {

    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}

/*
var ROOMS: Room[]=[
  {
  "id": "13932123",
  "roomNumber" : "409",
  "price" :"20",
  "links" : ""
},
{
  "id": "139324444",
  "roomNumber" : "509",
  "price" :"30",
  "links" : ""
},
{
  "id": "139324888",
  "roomNumber" : "609",
  "price" :"40",
  "links" : ""
}
] */
