// import * as W from './WLogin.style'
// import React from 'react';
// import googleIcon from '../../../img/login/icon-google.svg'
// import kakaoIcon from '../../../img/login/icon-kakao.svg'
//
//
// interface LoginProps {
//     google_login: () => void;
//     kakao_login: () => void;
// }
//
// export default function WLogin({google_login, kakao_login}: LoginProps) {
//     return (
//         <W.Container>
//             <W.Item />
//             <W.Box>
//                 <W.Text>
//                     MyBlogService
//                 </W.Text>
//                 <W.GoogleBtn onClick={google_login}>
//                     <img src={googleIcon} alt="google_logo" />
//                     <span>Google 계정으로 로그인</span>
//                 </W.GoogleBtn>
//                 <W.KakaoBtn onClick={kakao_login}>
//                     <img src={kakaoIcon} alt="kakao_logo" />
//                     <span>카카오 계정으로 로그인</span>
//                 </W.KakaoBtn>
//             </W.Box>
//         </W.Container>
//     )
// }