import React from 'react';
import * as L from './LoginStyle';
import WLogin from './WLogin'

//구글 로그인
const GoogleLoginUrl = `
https://accounts.google.com/o/oauth2/auth/oauthchooseaccount?response_type=code&client_id=${process.env.REACT_APP_GOOGLE_CLIENT_ID}&redirect_uri=${process.env.REACT_APP_GOOGLE_REDIRECT_URI}&scope=openid email profile
`;

//카카오 로그인
const kakaoLoginURL = `
https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.REACT_APP_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_REDIRECT_URI}
`;



//로그인함수 정의
const google_login = () => {
    localStroage.clear();
    window.open(GoogleLoginUrl, '_self');
}
const kakao_login = () => {
    localStroage.clear();
    window.open(kakaoLoginURL, '_self');
}
//Login Component
export const Login = () => {
    return (
        <L.Wrapper>
            <WLogin google_login={google_login} kakao_login={kakao_login} />
            {/* 추후 모바일 작업 할 예정 */}
            {/* <MLogin google_login={google_login} kakao_login={kakao_login} /> */}

        </L.Wrapper>
    )
}