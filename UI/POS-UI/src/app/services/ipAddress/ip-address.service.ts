import { Injectable, NgZone } from '@angular/core';

// declare global {
//   interface Window {
//      RTCPeerConnection: RTCPeerConnection;
//      mozRTCPeerConnection: RTCPeerConnection;
//      webkitRTCPeerConnection: RTCPeerConnection;
//   }
// }

@Injectable({
  providedIn: 'root'
})
export class IpAddressService {

  private ipRegex = new RegExp(/([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/);

  constructor(private zone: NgZone) { }


  async testPromise() {


    var promise = new Promise((resolve, reject) => {
    //  setTimeout(() => {
        console.log("Async Work Complete");

        let ipAddress: any = null;

        window.RTCPeerConnection = this.getRTCPeerConnection();

        const pc = new RTCPeerConnection({ iceServers: [] });
        pc.createDataChannel('');
        pc.createOffer().then(pc.setLocalDescription.bind(pc));

        pc.onicecandidate = (ice) => {
          this.zone.run(() => {
            if (!ice || !ice.candidate || !ice.candidate.candidate) {
              return;
            }
            let tt = ice.candidate.candidate;
            ipAddress = this.ipRegex.exec(ice.candidate.candidate);


            pc.onicecandidate = () => { };
            pc.close();

            resolve(ipAddress);
          });
        };

        
   //   }, 1000);
    });
    return promise;
  }





  determineLocalIp(): string {

    // var findIP = new Promise(r=>{var w=window,a=new (w.RTCPeerConnection||w.mozRTCPeerConnection||w.webkitRTCPeerConnection)({iceServers:[]}),b=()=>{};a.createDataChannel("");a.createOffer(c=>a.setLocalDescription(c,b,b),b);a.onicecandidate=c=>{try{c.candidate.candidate.match(/([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g).forEach(r)}catch(e){}}})
    // findIP.then(function(ip) {
    // console.log(ip);
    // })




    let ipAdress = this.getIp();
    return ipAdress;
  }




  getIp(): string {
    let ipAddress: any = null;
    window.RTCPeerConnection = this.getRTCPeerConnection();

    const pc = new RTCPeerConnection({ iceServers: [] });
    pc.createDataChannel('');
    pc.createOffer().then(pc.setLocalDescription.bind(pc));

    pc.onicecandidate = (ice) => {
      this.zone.run(() => {
        if (!ice || !ice.candidate || !ice.candidate.candidate) {
          return;
        }
        
        if (this.ipRegex.exec(ice.candidate.candidate) != null) {
          let ipAddressTMP = this.ipRegex.exec(ice.candidate.candidate);
          ipAddress = ipAddressTMP; // <unknown>ipAddressTMP[1];
        }
        else {
          ipAddress = '0.0.0.0'
        }

        localStorage.setItem('LOCAL_IP', ipAddress);

        pc.onicecandidate = () => { };
        pc.close();
      });
    };
    return ipAddress;
  }

  private getRTCPeerConnection() {
    return window.RTCPeerConnection;// ||
       //window.mozRTCPeerConnection ||
       //window.webkitRTCPeerConnection;
 }

}
