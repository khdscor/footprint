import React from 'react';

const MyLocationButton = ({setCenter, defaultCenter, setDefaultCenter, setMyLocation}) => {
  const success = (position) => {
    setDefaultCenter({lat: position.coords.latitude, lng: position.coords.longitude});
    setCenter({
      lat: () => position.coords.latitude,
      lng: () => position.coords.longitude
    })
    setMyLocation({
      lat: defaultCenter.lat+0.00000000000000000001,
      lng: defaultCenter.lng+0.00000000000000000001,
    })
  }

  const error = () => {
    alert("위치 권한을 허용해 주세dsadsa요");
  }

  return (<img
          src="/myLocation.png"
          alt="my image"
          style={{
            position: "fixed",
            zIndex: 10,
            bottom: "65px",
            right: "-27px",
            width: "150px",
            height: "150px",
            margin: "10px",
            cursor: "pointer"
          }}
          onClick={()=> navigator.geolocation.getCurrentPosition(success)}
      />
  );
};

export default MyLocationButton;