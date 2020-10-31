package org.robolectric.shadows;

import static org.robolectric.res.android.Errors.NO_ERROR;
import android.os.Build.VERSION_CODES;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.res.android.Ref;
import org.robolectric.res.android.Registries;
import org.robolectric.res.android.ResXMLParser;
import org.robolectric.res.android.ResXMLTree;
import org.robolectric.res.android.ResourceTypes.Res_value;
import org.xmlpull.v1.XmlPullParserException;

@Implements(className = "android.content.res.XmlBlock", isInAndroidSdk = false)
public class ShadowXmlBlock {

    @Implementation
    protected static Number nativeCreate(byte[] bArray, int off, int len) {
        System.out.println("ShadowXmlBlock#nativeCreate");
        if (bArray == null) {
            throw new NullPointerException();
        }
        int bLen = bArray.length;
        if (off < 0 || off >= bLen || len < 0 || len > bLen || (off + len) > bLen) {
            throw new IndexOutOfBoundsException();
        }
        // todo: optimize
        byte[] b = new byte[len];
        System.arraycopy(bArray, off, b, 0, len);
        ResXMLTree osb = new ResXMLTree(null);
        osb.setTo(b, len, true);
        if (osb.getError() != NO_ERROR) {
            throw new IllegalArgumentException();
        }
        return RuntimeEnvironment.castNativePtr(Registries.NATIVE_RES_XML_TREES.register(osb));
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetStringBlock(int obj) {
        System.out.println("ShadowXmlBlock#nativeGetStringBlock");
        return (int) nativeGetStringBlock((long) obj);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static Number nativeGetStringBlock(long obj) {
        System.out.println("ShadowXmlBlock#nativeGetStringBlock");
        ResXMLTree osb = Registries.NATIVE_RES_XML_TREES.getNativeObject(obj);
        return RuntimeEnvironment.castNativePtr(osb.getStrings().getNativePtr());
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeCreateParseState(int obj) {
        System.out.println("ShadowXmlBlock#nativeCreateParseState");
        return (int) nativeCreateParseState((long) obj);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static long nativeCreateParseState(long obj) {
        System.out.println("ShadowXmlBlock#nativeCreateParseState");
        ResXMLTree osb = Registries.NATIVE_RES_XML_TREES.getNativeObject(obj);
        ResXMLParser st = new ResXMLParser(osb);
        st.restart();
        return Registries.NATIVE_RES_XML_PARSERS.register(st);
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeNext(int state) throws XmlPullParserException {
        System.out.println("ShadowXmlBlock#nativeNext");
        return (int) nativeNext((long) state);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeNext(long state) throws XmlPullParserException {
        System.out.println("ShadowXmlBlock#nativeNext");
        ResXMLParser st = getResXMLParser(state);
        if (st == null) {
            return ResXMLParser.event_code_t.END_DOCUMENT;
        }
        do {
            int code = st.next();
            switch(code) {
                case ResXMLParser.event_code_t.START_TAG:
                    return 2;
                case ResXMLParser.event_code_t.END_TAG:
                    return 3;
                case ResXMLParser.event_code_t.TEXT:
                    return 4;
                case ResXMLParser.event_code_t.START_DOCUMENT:
                    return 0;
                case ResXMLParser.event_code_t.END_DOCUMENT:
                    return 1;
                case ResXMLParser.event_code_t.BAD_DOCUMENT:
                    // goto bad;
                    throw new XmlPullParserException("Corrupt XML binary file");
                default:
                    break;
            }
        } while (true);
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetNamespace(int state) {
        System.out.println("ShadowXmlBlock#nativeGetNamespace");
        return nativeGetNamespace((long) state);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetNamespace(long state) {
        System.out.println("ShadowXmlBlock#nativeGetNamespace");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getElementNamespaceID();
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetName(int state) {
        System.out.println("ShadowXmlBlock#nativeGetName");
        return (int) nativeGetName((long) state);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetName(long state) {
        System.out.println("ShadowXmlBlock#nativeGetName");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getElementNameID();
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetText(int state) {
        System.out.println("ShadowXmlBlock#nativeGetText");
        return (int) nativeGetText((long) state);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetText(long state) {
        System.out.println("ShadowXmlBlock#nativeGetText");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getTextID();
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetLineNumber(int state) {
        System.out.println("ShadowXmlBlock#nativeGetLineNumber");
        return (int) nativeGetLineNumber((long) state);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetLineNumber(long state) {
        System.out.println("ShadowXmlBlock#nativeGetLineNumber");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getLineNumber();
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetAttributeCount(int state) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeCount");
        return (int) nativeGetAttributeCount((long) state);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetAttributeCount(long state) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeCount");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getAttributeCount();
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetAttributeNamespace(int state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeNamespace");
        return (int) nativeGetAttributeNamespace((long) state, idx);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetAttributeNamespace(long state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeNamespace");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getAttributeNamespaceID(idx);
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetAttributeName(int state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeName");
        return (int) nativeGetAttributeName((long) state, idx);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetAttributeName(long state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeName");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getAttributeNameID(idx);
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetAttributeResource(int state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeResource");
        return (int) nativeGetAttributeResource((long) state, idx);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetAttributeResource(long state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeResource");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getAttributeNameResID(idx);
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetAttributeDataType(int state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeDataType");
        return (int) nativeGetAttributeDataType((long) state, idx);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetAttributeDataType(long state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeDataType");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getAttributeDataType(idx);
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetAttributeData(int state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeData");
        return (int) nativeGetAttributeData((long) state, idx);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetAttributeData(long state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeData");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getAttributeData(idx);
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetAttributeStringValue(int state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeStringValue");
        return (int) nativeGetAttributeStringValue((long) state, idx);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetAttributeStringValue(long state, int idx) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeStringValue");
        ResXMLParser resXMLParser = getResXMLParser(state);
        return resXMLParser.getAttributeValueStringID(idx);
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetIdAttribute(int obj) {
        System.out.println("ShadowXmlBlock#nativeGetIdAttribute");
        return (int) nativeGetIdAttribute((long) obj);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetIdAttribute(long state) {
        System.out.println("ShadowXmlBlock#nativeGetIdAttribute");
        ResXMLParser resXMLParser = getResXMLParser(state);
        int idx = resXMLParser.indexOfID();
        return idx >= 0 ? resXMLParser.getAttributeValueStringID(idx) : -1;
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetClassAttribute(int obj) {
        System.out.println("ShadowXmlBlock#nativeGetClassAttribute");
        return (int) nativeGetClassAttribute((long) obj);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetClassAttribute(long state) {
        System.out.println("ShadowXmlBlock#nativeGetClassAttribute");
        ResXMLParser resXMLParser = getResXMLParser(state);
        int idx = resXMLParser.indexOfClass();
        return idx >= 0 ? resXMLParser.getAttributeValueStringID(idx) : -1;
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetStyleAttribute(int obj) {
        System.out.println("ShadowXmlBlock#nativeGetStyleAttribute");
        return (int) nativeGetStyleAttribute((long) obj);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetStyleAttribute(long state) {
        System.out.println("ShadowXmlBlock#nativeGetStyleAttribute");
        ResXMLParser resXMLParser = getResXMLParser(state);
        int idx = resXMLParser.indexOfStyle();
        if (idx < 0) {
            return 0;
        }
        final Ref<Res_value> valueRef = new Ref<>(new Res_value());
        if (resXMLParser.getAttributeValue(idx, valueRef) < 0) {
            return 0;
        }
        Res_value value = valueRef.get();
        return value.dataType == org.robolectric.res.android.ResourceTypes.Res_value.TYPE_REFERENCE || value.dataType == org.robolectric.res.android.ResourceTypes.Res_value.TYPE_ATTRIBUTE ? value.data : 0;
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static int nativeGetAttributeIndex(int obj, String ns, String name) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeIndex");
        return (int) nativeGetAttributeIndex((long) obj, ns, name);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static int nativeGetAttributeIndex(long token, String ns, String name) {
        System.out.println("ShadowXmlBlock#nativeGetAttributeIndex");
        ResXMLParser st = getResXMLParser(token);
        if (st == null || name == null) {
            throw new NullPointerException();
        }
        int nsLen = 0;
        if (ns != null) {
            nsLen = ns.length();
        }
        return st.indexOfAttribute(ns, nsLen, name, name.length());
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static void nativeDestroyParseState(int obj) {
        System.out.println("ShadowXmlBlock#nativeDestroyParseState");
        nativeDestroyParseState((long) obj);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static void nativeDestroyParseState(long state) {
        System.out.println("ShadowXmlBlock#nativeDestroyParseState");
        Registries.NATIVE_RES_XML_PARSERS.unregister(state);
    }

    @Implementation(maxSdk = VERSION_CODES.KITKAT_WATCH)
    protected static void nativeDestroy(int obj) {
        System.out.println("ShadowXmlBlock#nativeDestroy");
        nativeDestroy((long) obj);
    }

    @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
    protected static void nativeDestroy(long obj) {
        System.out.println("ShadowXmlBlock#nativeDestroy");
        Registries.NATIVE_RES_XML_TREES.unregister(obj);
    }

    private static ResXMLParser getResXMLParser(long state) {
        return Registries.NATIVE_RES_XML_PARSERS.getNativeObject(state);
    }
}

