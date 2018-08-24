import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.junit.Test;
import pro.tools.data.image.ToolBarCode;

import java.io.IOException;
import java.util.UUID;

public class TestBarCode {

    String path = "E:/qrcode.png";

    @Test
    public void test1() throws IOException, WriterException {
        String str = "425a6839314159265359ad39bc0700007c9ef910067ff03f5f3cf0800a7fffffffffffffff8fe0c0028ec803308654d264d469ea3d2613d434da9899909a3469b44310302000c800d326401a212a7e9e421a53c189a053c34d29e991906a0068c0264320034c118d0e343434340340620680c800034d000d00c80000061228224d3d28fd468434d3ca07a9a32680680001a64f500d34343d468d3d13ca1f2cdf9ebe471be7a9cea7bb0401a2e36812750372948f21a98c90585c1648283a0d41438c2e680e14466e33384322a4e64d126e04084a311b4e522638c46b15d8bb06d99b684060229c03f28129a81080c5b86a1cc38aadc65672230e040525c0b068bc6d32eae630af32fad739b62a475b4ba04a62542106585fc58418633777187460a00d6bff773e0b57bf7e1671c3599661e6414ca081abe6c54bf85f7e562a530d67356877fc70c69307c14d570d7781fcead859b82cd963c320a5f9c70de9dda42e211080c4129fa2b96dd02b9b89c04134e65a47c73cc97b4ea5966cca4ab41bbb48393ed576cd6a985d37f0e6cf31354f32e4236064b6f045291c3a02363e918b146191181796790d0916f655d2ad1d4dd1422be572baab405693c8ca5c00e7e89098c1978d0b3cd2649257273793880b428deb16a5a5495486a4a1a99899c5b704c845254050178c8d2fb44aa18439646ab0bf060e0d2b4de7940ed366546de2d5c695b4c05773b1444a596a1633b48de2536a878ba9d62483fc8439bcdc9dfca91a6c20011626380e24601c271d295196fcd858ac50ed4a7fa42aeabfc94e4a585dfb0b9525e52bf73e82224d929d13b746180ca1472c487f5eba5c592e8f29e0b45428061c63c0c4cd2cf29c554964e16550a54e476f57d1e7157d349ac93f45aa280ab262c5f7a04456935302837002446f1d9f32e753b5830251890e6860cef5b076e013c1c753db2e4a38e4274a0881b45933e09b5dbc599d649051a453723ad56875210b14152b821d6b835eb3c0781523cfe9cf7aecc7f1cfaafcd26cb1823051664a5854a3694edb18537e11403cd27dcc524165305eacd4932e50ff177245385090ad39bc07";
//        ToolBarCode.encode(str,1000,1000,"PNG",path);
        ToolBarCode.encodeLogo(str, 1000, 1000, "E:/guohui.jfif", "PNG", path);
    }

    @Test
    public void test2() throws IOException, NotFoundException {
        String decode = ToolBarCode.decode(path);
        System.out.println(decode);
    }

    @Test
    public void test3() {
        int num = 50;
        for (int i = 0; i < num; i++) {
            String one = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            System.out.println(one);
        }
    }
}