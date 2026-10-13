import { NgZone, OnInit } from '@angular/core';
import { Component } from '@angular/core';
import * as $ from 'jquery'

declare global {
  interface Window {
    RTCPeerConnection: RTCPeerConnection;
    mozRTCPeerConnection: RTCPeerConnection;
    webkitRTCPeerConnection: RTCPeerConnection;
  }
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'pos-ui';
  localIp:any = sessionStorage.getItem('LOCAL_IP');
  private ipRegex = new RegExp(/([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/);

  constructor(private zone: NgZone) { }

  ngOnInit() {
    $('button').on('click', () => {
      alert('GeeksForGeeks');
    });
  //  this.determineLocalIp();
  }

  private determineLocalIp() {

    window.RTCPeerConnection = window.RTCPeerConnection;// || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;

    //this.getRTCPeerConnection();

    const pc = new RTCPeerConnection({
      iceServers: []
    });
    pc.createDataChannel('');
    pc.createOffer().then(pc.setLocalDescription.bind(pc));

    pc.onicecandidate = (ice) => {
      this.zone.run(() => {
        if (!ice || !ice.candidate || !ice.candidate.candidate) {
          return;
        }
        this.localIp = this.ipRegex.exec(ice.candidate.candidate);
        sessionStorage.setItem('LOCAL_IP', this.localIp);

        pc.onicecandidate = () => { };
        pc.close();
      });
    };
  }



}
