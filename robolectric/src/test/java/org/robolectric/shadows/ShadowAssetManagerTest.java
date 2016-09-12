package org.robolectric.shadows;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.AttributeSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.TestRunners;
import org.robolectric.annotation.Config;
import org.robolectric.util.Strings;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.util.TestUtil.joinPath;

@RunWith(TestRunners.MultiApiWithDefaults.class)
public class ShadowAssetManagerTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  AssetManager assetManager;

  @Before
  public void setUp() throws Exception {
    assetManager = Robolectric.buildActivity(Activity.class).create().get().getAssets();
  }

  @Test
  public void assertGetAssetsNotNull() {
    assertNotNull(assetManager);

    assetManager = RuntimeEnvironment.application.getAssets();
    assertNotNull(assetManager);

    assetManager = RuntimeEnvironment.application.getResources().getAssets();
    assertNotNull(assetManager);
  }

  @Test
  public void assetsPathListing() throws IOException {
    List<String> files;
    String testPath;

    testPath = "";
    files = Arrays.asList(assetManager.list(testPath));
    assertTrue(files.contains("docs"));
    assertTrue(files.contains("assetsHome.txt"));

    testPath = "docs";
    files = Arrays.asList(assetManager.list(testPath));
    assertTrue(files.contains("extra"));

    testPath = joinPath("docs", "extra");
    files = Arrays.asList(assetManager.list(testPath));
    assertTrue(files.contains("testing"));

    testPath = joinPath("docs", "extra", "testing");
    files = Arrays.asList(assetManager.list(testPath));
    assertTrue(files.contains("hello.txt"));

    testPath = "assetsHome.txt";
    files = Arrays.asList(assetManager.list(testPath));
    assertFalse(files.contains(testPath));

    testPath = "bogus.file";
    files = Arrays.asList(assetManager.list(testPath));
    assertEquals(0, files.size());
  }

  @Test
  public void open_shouldOpenFile() throws IOException {
    final String contents = Strings.fromStream(assetManager.open("assetsHome.txt"));
    assertThat(contents).isEqualTo("assetsHome!");
  }

  @Test
  public void open_withAccessMode_shouldOpenFile() throws IOException {
    final String contents = Strings.fromStream(assetManager.open("assetsHome.txt", AssetManager.ACCESS_BUFFER));
    assertThat(contents).isEqualTo("assetsHome!");
  }

  @Test
  public void openFd_shouldProvideFileDescriptorForAsset() throws Exception {
    AssetFileDescriptor assetFileDescriptor = assetManager.openFd("assetsHome.txt");
    assertThat(Strings.fromStream(assetFileDescriptor.createInputStream())).isEqualTo("assetsHome!");
    assertThat(assetFileDescriptor.getLength()).isEqualTo(11);
  }

  @Test
  public void openNonAssetShouldOpenRealAssetFromResources() throws IOException {
    InputStream inputStream = assetManager.openNonAsset(0, "./res/drawable/an_image.png", 0);

    ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream) inputStream;
    assertThat(byteArrayInputStream.available()).isEqualTo(6559);
  }

  @Test
  public void openNonAssetShouldOpenRealAssetFromAndroidJar() throws IOException {
    // Not the real full path (it's in .m2/repository), but it only cares about the last folder and file name
    final String jarFile = "jar:/android-all-5.0.0_r2-robolectric-0.jar!/res/drawable-hdpi/bottom_bar.png";

    InputStream inputStream = assetManager.openNonAsset(0, jarFile, 0);
    assertThat(((ByteArrayInputStream) inputStream).available()).isEqualTo(389);
  }

  @Test
  public void openNonAssetShouldThrowExceptionWhenFileDoesNotExist() throws IOException {
    expectedException.expect(IOException.class);
    expectedException.expectMessage("Unable to find resource for ./res/drawable/does_not_exist.png");

    assetManager.openNonAsset(0, "./res/drawable/does_not_exist.png", 0);
  }

  @Test
  @Config(qualifiers = "mdpi")
  public void openNonAssetShouldOpenCorrectAssetBasedOnQualifierMdpi() throws IOException {
    InputStream inputStream = assetManager.openNonAsset(0, "./res/drawable/robolectric.png", 0);

    ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream) inputStream;
    assertThat(byteArrayInputStream.available()).isEqualTo(8141);
  }

  @Test
  @Config(qualifiers = "hdpi")
  public void openNonAssetShouldOpenCorrectAssetBasedOnQualifierHdpi() throws IOException {
    InputStream inputStream = assetManager.openNonAsset(0, "./res/drawable/robolectric.png", 0);

    ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream) inputStream;
    assertThat(byteArrayInputStream.available()).isEqualTo(23447);
  }

  @Test
  public void attrsToTypedArray_shouldAllowMockedAttributeSets() throws Exception {
    AttributeSet mockAttributeSet = mock(AttributeSet.class);
    when(mockAttributeSet.getAttributeCount()).thenReturn(1);
    when(mockAttributeSet.getAttributeNameResource(0)).thenReturn(android.R.attr.windowBackground);
    when(mockAttributeSet.getAttributeValue(0)).thenReturn("value");

    Resources resources = RuntimeEnvironment.application.getResources();
    resources.obtainAttributes(mockAttributeSet, new int[]{android.R.attr.windowBackground});
  }
}
