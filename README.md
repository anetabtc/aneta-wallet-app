# Aneta Wallet App

[comment]: <> (<img src="https://user-images.githubusercontent.com/26038055/131368542-0e401c2c-35e4-449c-8423-ea259b39614b.png" align="right"  width="250">)

[comment]: <> (Official Ergo Wallet App &#40;[official announcement]&#40;https://ergoplatform.org/en/blog/2021-07-29-ergo-for-android-released/&#41;&#41;)

[comment]: <> (<a href="https://play.google.com/store/apps/details?id=org.ergoplatform.android"><img alt="Get it on Google Play" src="https://user-images.githubusercontent.com/11427267/75923897-483f3b00-5e66-11ea-8ec7-e86887afea51.png"></a>)

[comment]: <> (<a href="https://testflight.apple.com/join/MRyG2qfm"><img alt="Download App Store" src="https://user-images.githubusercontent.com/11427267/75923896-47a6a480-5e66-11ea-87c1-3ec73ebcf7a5.png"></a>)

[comment]: <> (Features:)

[comment]: <> (* generating wallets, restoring wallets in a way compatible to Yoroi and Ergo node)

[comment]: <> (* you can add read-only wallets without entering your secrets to watch balance or to prepare transactions for [cold wallet devices]&#40;https://github.com/ergoplatform/ergo-wallet-app/wiki/Cold-wallet&#41;)

[comment]: <> (* no need to make a full sync, this is a lightweight client)

[comment]: <> (* Requesting payments by showing QR code or sharing a link)

[comment]: <> (* Sending payments, manually or by scanning a QR code)

[comment]: <> (* Displays and sends tokens and NFT)

[comment]: <> (* Your secrets are stored password-encrypted or authentication-protected)

[comment]: <> (* Show wallet balance, configurable comparison fiat currency)

[comment]: <> (* Cold wallet capable &#40;[more information]&#40;https://github.com/ergoplatform/ergo-wallet-app/wiki/Cold-wallet&#41;&#41;)

[comment]: <> (* ErgoPay support)

[comment]: <> (You need at least Android 7 or iOS 13 to run Ergo Wallet.)

[comment]: <> (Visit the [Ergo Discord]&#40;https://discord.gg/kj7s7nb&#41; to give feedback.)

[comment]: <> (### Download and install the APK manually)

[comment]: <> (Apart from Google Play, you can download the app APKs from the [releases section]&#40;https://github.com/ergoplatform/ergo-wallet-app/releases&#41; to sideload.)

[comment]: <> (There are APKs available for Testnet and Mainnet, and as a debug build and release build.)

[comment]: <> (**Debug builds** are built on GitHub.)

[comment]: <> (It is normal that Google Play Protect warns about)

[comment]: <> (an unsafe app. GitHub builds the binaries with a certificate unknown to Google.)

[comment]: <> (Because the certificate changes, you can't upgrade the app later - you need to uninstall and install fresh.)

[comment]: <> (**Release builds** are built by me with my developer certificate and minified. This should reduce)

[comment]: <> (Google Play Protect warnings, you'll be able to upgrade without losing your data and the app is)

[comment]: <> (much smaller and faster, however, you have to trust me.)

[comment]: <> (The APK file can be installed on your Android device. If you sideload for the first time,)

[comment]: <> ([you can follow this guide]&#40;https://www.xda-developers.com/sideload-apps-how-to/&#41;.)

[comment]: <> (### Build yourself)

[comment]: <> (* [Android]&#40;android/BUILD.md&#41;)

[comment]: <> (* [iOS]&#40;ios/BUILD.md&#41;)

[comment]: <> (### Translations)

[comment]: <> (Every translation is welcome! There is a single )

[comment]: <> ([strings file to translate]&#40;https://github.com/ergoplatform/ergo-wallet-app/blob/develop/android/src/main/res/values/strings.xml&#41; )

[comment]: <> (to your language.)

[comment]: <> (Either send me the translated file on Discord or Telegram, or open a PR here. For this, move the )

[comment]: <> (file to a values-xx directory where xx is your language's ISO code. )

[comment]: <> (&#40;[Spanish example]&#40;https://github.com/ergoplatform/ergo-wallet-app/tree/develop/android/src/main/res/values-es&#41;&#41;)

[comment]: <> (Thanks in advance!)

[comment]: <> (### Tip the developer)

[comment]: <> (If you want to tip the developer for making this app, thanks in advance! Send your tips to)

[comment]: <> ([9ewA9T53dy5qvAkcR5jVCtbaDW2XgWzbLPs5H4uCJJavmA4fzDx]&#40;https://explorer.ergoplatform.com/payment-request?address=9ewA9T53dy5qvAkcR5jVCtbaDW2XgWzbLPs5H4uCJJavmA4fzDx&amount=0&description=&#41;)

[comment]: <> (### Testing on Testnet)

[comment]: <> (You can test the testnet Android debug build on testnet or build the iOS version yourself for testnet. Generate a new wallet and send)

[comment]: <> (yourself some test Ergos by visiting https://testnet.ergofaucet.org/)

