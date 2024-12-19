/*
 * MIT License
 *
 * Copyright (c) 2022 Srikanth
 * Copyright (c) 2022 Srikanth
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ui.base;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.config.ConfigurationManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.RequestOptions;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.ui.utilities.Reporter;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class PlaywrightWrapper extends Reporter {

    int retry = 0;
    double timeOut = 40000;
    String attachment = "";

    /**
     * Load the URL on the browser launched
     *
     * @param url The http(s) URL that to be loaded on the browser
     * @return true if the load is successful else false
     * @author Srikanth
     */
    public boolean navigate(String url) {
        try {
            getPage().navigate(url);
            reportStep("The page with URL : " + url + " is loaded", "info");
            reportPass("Launched the application successfully");
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    public void navigateToBack() {
        try {
            getPage().goBack();
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
    }

    /**
     * Maximize the browser based on screen size
     * Presently there is no built-in method to invoke.
     *
     * @author Srikanth
     */
    public void maximize() {
        try {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            getPage().setViewportSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        } catch (HeadlessException e) {

        }
    }

    /**
     * Check if the given selector of the element is visible or not after 2 seconds
     *
     * @param locator The css / xpath / or playwright supported locator and element to provide the name of the element
     * @return true if the element is visible else false
     * @author Srikanth
     */
    public boolean isVisible(String locator, String elementName) {
        boolean bVisible = false;
        try {
            waitForAppearance(locator);
            bVisible = getPage().isVisible(locator);
            if (bVisible == true) {
                reportStep("The WebElement : " + elementName + " is visible ", "info");
            } else {
                reportStep("The WebElement : " + elementName + " is not visible ", "warning");
            }

        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("The WebElement : " + elementName + " is not visible ", "fail");
        }
        getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
        return bVisible;

    }

    /**
     * Check if the given selector of the element is visible or not after 2 seconds
     *
     * @param locator The css / xpath / or playwright supported locator and element to provide the name of the element
     * @return true if the element is visible else false
     * @author Srikanth
     */

    public boolean isVisible(String locator) {
        boolean bVisible = false;
        try {
            pause("low");
            bVisible = getPage().isVisible(locator);

        } catch (PlaywrightException e) {
            e.printStackTrace();
        }
        return bVisible;
    }

    /**
     * Check if the given selector of the element is not visible or not after 2 seconds
     *
     * @param locator The css / xpath / or playwright supported locator and element to provide the name of the element
     * @return true if the element is not visible else false
     * @author Srikanth
     */
    public boolean isNotVisible(String locator, String elementName) {
        boolean bVisible = false;
        try {
            pause("low");
            bVisible = getPage().isVisible(locator);
            if (bVisible) {
                reportStep("The WebElement '" + elementName + "' is visible ", "warning");
            } else {
                reportPass("The WebElement '" + elementName + "' is not visible.");
            }

        } catch (PlaywrightException e) {
            e.printStackTrace();
        }

        return bVisible;

    }


    /**
     * Use this method to clear into an element.
     *
     * @return true if the value is typed else false
     * @author Srikanth
     */
    public boolean clear(String locator) {
        try {
            getPage().locator(locator).clear();
            reportStep("The text box contents is cleared", "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to typing into an element, which may set its value.
     *
     * @param locator The locator to identify the element
     * @param value   The value to be entered in the text
     * @param name    The name of the text field (label)
     * @return true if the value is typed else false
     * @author Srikanth
     */
    public boolean typeWithType(String locator, String value, String name) {
        try {
            getPage().locator(locator).type(value);
            reportStep("The text box : " + name + " is typed with value : " + value, "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }


    /**
     * Use this method to typing into an element, which may set its value.
     *
     * @param locator The locator to identify the element
     * @param value   The value to be entered in the text
     * @param name    The name of the text field (label)
     * @return true if the value is typed else false
     * @author Srikanth
     */
    public boolean type(String locator, String value, String name) {
        try {
            getPage().locator(locator).fill("");
            getPage().locator(locator).fill(value);
            reportStep("The text box : " + name + " is typed with value : " + value, "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }


    /**
     * Use this method to typing into an element inside a frame, which may set its value.
     *
     * @param locator The locator to identify the element
     * @param value   The value to be entered in the text
     * @param name    The name of the text field (label)
     * @return true if the value is typed else false
     * @author Srikanth
     */
    public boolean typeInFrame(String locator, String value, String name) {
        try {
            getFrameLocator().locator(locator).fill("");
            ;
            getFrameLocator().locator(locator).fill(value);
            reportStep("The text box : " + name + " is typed with value : " + value, "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to typing into an element and perform <ENTER> key.
     *
     * @param locator The locator to identify the element
     * @param value   The value to be entered in the text
     * @param name    The name of the text field (label)
     * @return true if the value is typed else false
     * @author Srikanth
     */
    public boolean typeAndEnter(String locator, String value, String name) {
        try {
            getPage().locator(locator).fill("");
            ;
            getPage().locator(locator).fill(value);
            pause("low");
            getPage().locator(locator).press("Enter");
            reportStep("The text box : " + name + " is typed with value : " + value, "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to typing into an element and perform <SPACE> key.
     *
     * @param locator The locator to identify the element
     * @param value   The value to be entered in the text
     * @param name    The name of the text field (label)
     * @return true if the value is typed else false
     * @author Srikanth
     */

    public boolean typeAndSpace(String locator, String value, String name) {
        try {
            getPage().locator(locator).fill("");
            ;
            getPage().locator(locator).fill(value);
            pause("low");
            getPage().locator(locator).press("Space");
            reportStep("The text box : " + name + " is typed with value : " + value, "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to typing into an element and press Down Arrow key and press Enter.
     *
     * @param locator The locator to identify the element
     * @return void
     * @author Srikanth
     */

    public void pressDownArrowAndEnter(String locator) {
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
            getPage().locator(locator).waitFor();
            getPage().locator(locator).scrollIntoViewIfNeeded();
            getPage().locator(locator).click();
            getPage().locator(locator).press("ArrowDown");
            getPage().locator(locator).press("Enter");
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }

    }

    /**
     * Use this method to typing into an element and press Down Arrow key.
     *
     * @param locator The locator to identify the element
     * @return void
     * @author Srikanth
     */
    public void pressDownArrow(String locator) {
        try {
            getPage().locator(locator).press("ArrowDown");
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }

    }

    /**
     * Use this method to upload a file into the chosen field.
     *
     * @param locator  The locator to identify the element where need to upload
     * @param fileName The file name (relative or absolute)
     * @param name     The name of the upload field (label)
     * @return true if the file is uploaded else false
     * @author Srikanth
     */
    public boolean uploadFile(String locator, String fileName, String name) {
        try {
            getPage().locator(locator).setInputFiles(Paths.get(fileName));
            reportStep("The text box :" + name + " is uploaded with file :" + fileName, "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to click a button.
     *
     * @param locator The locator to identify the element
     * @param name    The name of the button field (label)
     * @return true if the button is clicked else false
     * @author Srikanth
     */
    public boolean click(String locator, String name) {
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
            getPage().locator(locator).waitFor();
            getPage().locator(locator).scrollIntoViewIfNeeded();
            getPage().locator(locator).click();
            reportStep("The Button name : " + name + " is clicked", "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to click a Random Place in the Screen.
     *
     * @return true if the button is clicked else false
     * @author Srikanth
     */
    public boolean randomDoubleClick() {
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
            int width = getPage().viewportSize().width;
            int height = getPage().viewportSize().height;

            // Generate random coordinates within the viewport
            int randomX = (int) (Math.random() * width);
            int randomY = (int) (Math.random() * height);
            getPage().mouse().dblclick(randomX, randomY);
            reportStep("The Random Coordinates X and Y is double clicked ", "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to scroll to the element is viewable using JS.
     *
     * @param locator The locator to identify the element
     * @author Srikanth
     */
    public void scrollUsingJS(String locator) {
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
            Locator jsElement = getPage().locator(locator);
            getPage().evaluate("arguments[0].scrollIntoView(true);", jsElement);
            reportStep("Page Scroll is performed until element is displayed", "info");
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }

    }

    /**
     * Use this method to the scroll using the mouse wheel using x & y coordinate.
     *
     * @param locator The element locator to get x-y coordinate
     * @author Srikanth
     */
    public void scrollUsingMouseWheelVertically(String locator) {
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());

            Locator element = getPage().locator(locator);
            BoundingBox boundingBox = element.boundingBox();
            if (boundingBox != null) {
                double y = boundingBox.y;
                getPage().mouse().wheel(0.0, y);
            }
            reportStep("Page Scroll is performed based on co-ordinates", "info");
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }

    }

    public boolean clickAndType(String locator, String typeValue, String name) {
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
            getPage().locator(locator).click();
            getPage().locator(locator).fill("");
            ;
            getPage().locator(locator).fill(typeValue);
            reportStep("The text box :" + name + " is typed with value :" + typeValue, "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    public void scrollToElement(String locator) {
        getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
        getPage().locator(locator).waitFor();
        getPage().locator(locator).scrollIntoViewIfNeeded();
    }


    /**
     * Use this method to click an element.
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element field (label)
     * @param type    The type of the element such as link, element etc
     * @return true if the element is clicked else false
     * @author Srikanth
     */
    public boolean click(String locator, String name, String type) {
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
            getPage().locator(locator).scrollIntoViewIfNeeded();
            getPage().locator(locator).click();
            reportStep("The " + type + " :" + name + " is clicked", "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    public void clickUsingJs(String locator) {
        getPage().evaluate("() => { " + "const element = document.evaluate(\"" + locator + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " + "if (element) { " + "element.scrollIntoView(); " + "element.click(); " + "} " + "}");

    }

    /**
     * Use this method to double click an element.
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element field (label)
     * @param type    The type of the element such as link, element etc
     * @return true if the element is clicked else false
     * @author Srikanth
     */
    public boolean doubleClick(String locator, String name, String type) {
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
            getPage().locator(locator).scrollIntoViewIfNeeded();
            getPage().locator(locator).dblclick();
            reportStep("The " + type + " :" + name + " is double clicked", "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to click an element within a frame.
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element field (label)
     * @param type    The type of the element such as link, element etc
     * @return true if the element is clicked else false
     * @author Srikanth
     */
    public boolean clickInFrame(String locator, String name) {
        try {
            getFrameLocator().locator(locator).scrollIntoViewIfNeeded();
            getFrameLocator().locator(locator).click();
            reportStep("The button : " + name + " is clicked", "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to check a checkbox.
     *
     * @param locator The locator to identify the checkbox
     * @param name    The name of the checkbox field (label)
     * @return true if the checkbox is checked else false
     * @author Srikanth
     */
    public boolean check(String locator, String name) {
        try {
            getPage().locator(locator).check();
            reportStep("The checkbox: " + name + " is checked", "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to select a dropdown by its text.
     *
     * @param locator The locator to identify the dropdown
     * @param text    The text to be selected in the dropdown
     * @param name    The name of the dropdown field (label)
     * @return true if the dropdown is selected else false
     * @author Srikanth
     */
    public boolean selectByText(String locator, String text, String name) {
        try {
            getPage().locator(locator).selectOption(new SelectOption().setLabel(text));
            reportStep("The drop down : " + name + " is selected with value : " + text, "info");
            return true;
        } catch (PlaywrightException e) {
            e.getMessage();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to Highlight the element.
     *
     * @param locator The locator to identify the dropdown
     * @param name    The name of the highlight element
     * @return true if the dropdown is selected else false
     * @author Srikanth
     */
    public boolean highlightElement(String locator, String name) {
        try {
            Locator element = getPage().locator(locator);
            getPage().evaluate("element => element.setAttribute('style', 'border: 2px solid red; background: yellow;')", element);
            reportStep("The locator : " + name + " is Highlighted successfully", "info");
            return true;
        } catch (PlaywrightException e) {
            e.getMessage();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }


    /**
     * Use this method to select a dropdown by its value.
     *
     * @param locator The locator to identify the dropdown
     * @param value   The value based on which it to be selected in the dropdown
     * @param name    The name of the dropdown field (label)
     * @return true if the dropdown is selected else false
     * @author Srikanth
     */
    public boolean selectByValue(String locator, String value, String name) {
        try {
            getPage().locator(locator).selectOption(value);
            reportStep("The drop down : " + name + " is selected with value index as : " + value, "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to select a dropdown by its index.
     *
     * @param locatorId The locator to identify the dropdown
     * @param index     The index to be selected in the dropdown (starts at 0)
     * @param name      The name of the dropdown field (label)
     * @return true if the dropdown is selected else false
     * @author Srikanth
     */
    public boolean selectByIndex(String locatorId, int index, String name) {
        try {
            Locator locator = getPage().locator(locatorId + " > option");
            if (index > locator.count() || index < 0)
                index = (int) Math.floor(Math.random() * (locator.count() - 1)) + 1;
            getPage().locator(locatorId).selectOption(locator.nth(index).getAttribute("value"));
            reportStep("The drop down : " + name + " is selected with index : " + index, "info");
            return true;
        } catch (PlaywrightException e) {
            e.getMessage();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to select a dropdown by its index inside a frame.
     *
     * @param locatorId The locator to identify the dropdown
     * @param index     The index to be selected in the dropdown (starts at 0)
     * @param name      The name of the dropdown field (label)
     * @return true if the dropdown is selected else false
     * @author Srikanth
     */
    public boolean selectByIndexInFrame(String locatorId, int index, String name) {
        try {
            Locator locator = getFrameLocator().locator(locatorId + " > option");
            if (index > locator.count() || index < 0)
                index = (int) Math.floor(Math.random() * (locator.count() - 1)) + 1;
            getFrameLocator().locator(locatorId).selectOption(locator.nth(index).getAttribute("value"));
            reportStep("The drop down : " + name + " is selected with index : " + index, "info");
            return true;
        } catch (PlaywrightException e) {
            e.getMessage();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to select a dropdown by the random index.
     *
     * @param locator The locator to identify the dropdown
     * @param name    The name of the dropdown field (label)
     * @return true if the dropdown is selected else false
     * @author Srikanth
     */
    public boolean selectByRandomIndex(String locator, String name) {
        return selectByIndex(locator, -1, name);
    }

    /**
     * Use this method to type and choose an element (that looks like dropdown)
     *
     * @param ddLocator     The Dropdown locator to identify the main select element
     * @param optionLocator The Option locator to identify the item element
     * @param option        The option to be entered in the text area
     * @param name          The name of the dropdown field (label)
     * @return true if the option is selected else false
     * @author Srikanth
     */
    public boolean clickAndType(String ddLocator, String optionLocator, String option, String name) {
        try {
            getPage().locator(ddLocator).click();
            getPage().locator(optionLocator).fill(option);
            getPage().keyboard().press("Enter");
            reportStep("The drop down : " + name + " is selected with value : " + option, "info");
            return true;
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to click and choose an element (that looks like dropdown)
     *
     * @param ddLocator     The Dropdown locator to identify the main select element
     * @param optionLocator The Option locator to identify the item element
     * @param option        The option to be selected in the dropdown
     * @param name          The name of the dropdown field (label)
     * @return true if the option is selected else false
     * @author Srikanth
     */
    public boolean clickAndChoose(String ddLocator, String optionLocator, String option, String name) {
        try {
            getPage().locator(ddLocator).click();
            pause("low");
            getPage().locator(optionLocator).click();
            reportStep("The drop down : " + name + " is selected with value : " + option, "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to click and choose an element (that looks like dropdown) inside a frame
     *
     * @param ddLocator     The Dropdown locator to identify the main select element
     * @param optionLocator The Option locator to identify the item element
     * @param option        The option to be selected in the dropdown
     * @param name          The name of the dropdown field (label)
     * @return true if the option is selected else false
     * @author Srikanth
     */
    public boolean clickAndChooseInFrame(String ddLocator, String optionLocator, String option, String name) {
        try {
            getFrameLocator().locator(ddLocator).click();
            getFrameLocator().locator(optionLocator).click();
            reportStep("The drop down : " + name + " is selected with value : " + option, "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to mouse over an element
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element (label)
     * @return true if the mouse over is done else false
     * @author Srikanth
     */
    public boolean mouseOver(String locator, String name) {
        try {
            getPage().locator(locator).hover();
            reportStep("The element : " + name + " is moused over successfully", "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    public boolean mouseDown() {
        try {
            getPage().mouse().wheel(0, 200);
            reportStep("Move mouse down", "info");
            return true;
        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to get inner text of an element
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element (label)
     * @return true if the mouse over is done else false
     * @author Srikanth
     */
    public String getInnerText(String locator) {
        try {
            return getPage().locator(locator).innerText();
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * Use this method to check if the element is enabled
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element (label)
     * @return true if the element is enabled else false
     * @author Srikanth
     */
    public boolean isEnabled(String locator, String name) {
        boolean bEnabled = false;
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
            bEnabled = getPage().isEnabled(locator);
            reportStep("The '" + name + "' is enabled ", "info");
            return bEnabled;
        } catch (Exception e) {
            e.printStackTrace();
            reportStep("The '" + name + "' is disabled ", "fail");
        }
        return bEnabled;
    }

    /**
     * Use this method to check if the element is disabled
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element (label)
     * @return true if the element is disabled else false
     * @author Srikanth
     */

    public boolean isDisabled(String locator, String name) {
        boolean bEnabled = false;
        try {
            getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
            bEnabled = getPage().isDisabled(locator);
            reportStep("The '" + name + "' is disabled ", "info");
            return bEnabled;
        } catch (Exception e) {
            e.printStackTrace();
            reportStep("The '" + name + "' is not disabled ", "fail");
        }
        return bEnabled;
    }


    /**
     * Use this method to check if the element is disabled
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element (label)
     * @return true if the element is editable else false
     * @author Srikanth
     */
    public boolean isEditable(String locator, String elementName) {
        boolean bEditable = false;
        try {
            waitForAppearance(locator);
            bEditable = getPage().isEditable(locator);
            if (bEditable == true) {
                reportStep("The WebElement : " + elementName + " is editable ", "info");
            } else {
                reportStep("The WebElement : " + elementName + " is not editable ", "warning");
            }

        } catch (PlaywrightException e) {
            e.printStackTrace();
            reportStep("The WebElement : " + elementName + " is not visible ", "fail");
        }
        return bEditable;
    }

    /**
     * Use this method to report if the element is disabled
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element (label)
     * @return true if the element is disabled else false
     * @author Srikanth
     */
    public boolean verifyDisabled(String locator, String label) {
        try {
            if (isDisabled(locator, label)) reportStep("The element : " + label + " is disabled as expected", "info");
            else reportStep("The element : " + label + " is enabled", "warning");
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Use this method to check if element should not have value.
     *
     * @param locator - Locator of the element
     * @param label   - Name of the element
     */
    public void verifyAttributeIsNotEmpty(String locator, String attribute, String label) {
        try {
            String getValue = getAttribute(locator, attribute);
            if (getValue.isEmpty() || getValue.equals(" "))
                reportStep("The element attribute: " + label + " is empty.", "fail");
            else reportStep("The element '" + label + "' has value as - " + getValue, "info");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Use this method to wait for the element to disappear
     *
     * @param locator The locator to identify the element
     * @return true if the element is disappeared else false
     * @author Srikanth
     */
    public boolean waitForDisappearance(String locator) {
        try {
            getPage().locator(locator).waitFor(new Locator.WaitForOptions().setTimeout(ConfigurationManager.configuration().pauseHigh()).setState(WaitForSelectorState.HIDDEN));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Use this method to wait for the element to appear
     *
     * @param locator The locator to identify the element
     * @return true if the element is appeared else false
     * @author Srikanth
     */
    public boolean waitForAppearance(String locator) {
        try {
            getPage().locator(locator).waitFor(new Locator.WaitForOptions().setTimeout(ConfigurationManager.configuration().pauseHigh()).setState(WaitForSelectorState.VISIBLE));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Use this method to report the correctness of the title
     *
     * @param title The title of the browser
     * @return true if the title matches the partial content else false
     * @author Srikanth
     */
    public boolean verifyTitle(String title) {
        try {
            if (getPage().title().contains(title)) {
                reportStep("The page with title : " + title + " displayed as expected", "info");
                return true;
            } else reportStep("The page with title : " + title + " did not match", "warning");

        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to report the correctness of the inner text (Exact Match)
     *
     * @param locator      The locator to identify the element
     * @param expectedText The expected text to be verified
     * @return true if the inner text matches the exact content else false
     * @author Srikanth
     */
    public boolean verifyExactText(String locator, String expectedText) {
        try {
            if (getPage().locator(locator).innerText().equals(expectedText)) {
                reportStep("The element with text : " + expectedText + " displayed as expected", "pass");
                return true;
            } else reportStep("The element with text : " + expectedText + " did not match", "warning");

        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to report the correctness of the inner text (Partial Match)
     *
     * @param locator      The locator to identify the element
     * @param expectedText The expected text to be verified
     * @return true if the inner text matches the partial content else false
     * @author Srikanth
     */
    public boolean verifyPartialText(String locator, String expectedText) {
        try {
            if (getPage().locator(locator).innerText().contains(expectedText)) {
                reportStep("The element with text : " + expectedText + " displayed as expected", "pass");
                return true;
            } else reportStep("The element with text : " + expectedText + " did not match", "warning");

        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to report the current url
     *
     * @return
     * @author Srikanth
     */
    public String getCurrentURL(String pageName) {
        String url = null;
        try {
            url = getPage().url();
            reportStep(pageName + " Current URL is : " + url, "info");
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return url;
    }

    /**
     * Use this method return the text typed within a textbox/area
     *
     * @param locator The locator to identify the element
     * @return Returns the text typed
     * @author Srikanth
     */
    public String getInputText(String locator) {
        try {
            return getPage().locator(locator).inputValue();
        } catch (PlaywrightException e) {
        }
        return "";
    }

    /**
     * Use this method return the Locatorscount
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element
     * @return Returns the int
     * @author Srikanth
     */
    public int getLocatorsCount(String locator) {
        Locator locators = null;
        try {
            locators = getPage().locator(locator);
            return locators.count();
        } catch (PlaywrightException e) {
        }
        return locators.count();
    }

    /**
     * Use this method return the Locators
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element
     * @return Returns the locators
     * @author Srikanth
     */
    public Locator getLocators(String locator) {
        Locator locators = null;
        try {
            locators = getPage().locator(locator);
            return locators;
        } catch (PlaywrightException e) {
        }
        return locators;
    }

    /**
     * Use this method return the List of Locators
     * @param locator The locator to identify the element
     * @return Returns the locators
     * @author Srikanth
     */
//	public List<ElementHandle> getListElements(String locator){
//		try {
//			getPage().locator(locator).elementHandles();
//		}catch (PlaywrightException e){}
//		return getPage().locator(locator).elementHandles();
//	}


    /**
     * Use this method to report the correctness of the typed text (Partial Match)
     *
     * @param locator      The locator to identify the element
     * @param expectedText The expected text to be verified
     * @return true if the typed text matches the partial content else false
     * @author Srikanth
     */
    public boolean verifyInputText(String locator, String expectedText) {
        try {
            if (getPage().locator(locator).inputValue().contains(expectedText)) {
                reportStep("The element with text : " + expectedText + " displayed as expected", "pass");
                return true;
            } else reportStep("The element with text : " + expectedText + " did not match", "warning");

        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    /**
     * Use this method to report the correctness of the typed text (Partial Match)
     *
     * @param locator      The locator to identify the element
     * @param expectedText The expected text to be verified
     * @return true if the typed text matches the partial content else false
     * @author Srikanth
     */
    public boolean verifyInnerText(String actual, String expectedText) {
        try {
            if (actual.contains(expectedText)) {
                reportStep("The actual value '" + actual + "' is matched with expected value '" + expectedText + "'", "pass");
                return true;
            } else
                reportStep("The actual value '" + actual + "' is not matched with expected value '" + expectedText + "'", "fail");

        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }


    /**
     * Use this method to get the attribute of the element
     *
     * @param locator   The locator to identify the element
     * @param attribute The attribute of the element
     * @return The attribute value of the located element
     * @author Srikanth
     */
    public String getAttribute(String locator, String attribute) {
        try {
            return getPage().locator(locator).getAttribute(attribute);
        } catch (PlaywrightException e) {
        }
        return "";
    }

    /**
     * Use this method to verify attribute of the element (Partial Match)
     *
     * @param locator      The locator to identify the element
     * @param attribute    The attribute of the element
     * @param expectedText The expected attribute value of the located element
     * @return true if the attribute matches the partial content else false
     * @author Srikanth
     */
    public boolean verifyAttribute(String locator, String attribute, String expectedText) {
        try {
            if (getPage().locator(locator).getAttribute(attribute).equalsIgnoreCase(expectedText)) {
                reportStep("The element with text : " + expectedText + " displayed as expected", "pass");
                return true;
            } else reportStep("The element with text : " + expectedText + " did not match", "warning");

        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;
    }

    /**
     * Use this method to report pass
     * * @param reporting to define the steps
     *
     * @author Srikanth
     */
    public void reportPass(String reporting) {
        reportStep(reporting, "pass");
    }

    /**
     * Use this method to report fail and throw back exception to stop the execution
     * * @param reporting to define the steps
     *
     * @author Srikanth
     */
    public void reportFailAndThrowException(String reporting) {

        reportStep(reporting, "fail");
        throw new PlaywrightException(reporting);
    }

    /**
     * Use this method to report fail and throw back exception to stop the execution
     * * @param reporting to define the steps
     *
     * @author Srikanth
     */
    public void reportFail(String reporting) {
        reportStep(reporting, "fail");
    }

    /**
     * Use this method to report fail and throw back exception to stop the execution
     * * @param reporting to define the steps
     *
     * @author Srikanth
     */
    public void reportWarning(String reporting) {
        reportStep(reporting, "warning");
    }

    /**
     * Use this method to verify attribute of the element (Partial Match) inside a frame
     *
     * @param locator      The locator to identify the element
     * @param attribute    The attribute of the element
     * @param expectedText The expected attribute value of the located element
     * @return true if the attribute matches the partial content else false
     * @author Srikanth
     */
    public boolean verifyAttributeInFrame(String locator, String attribute, String expectedText) {
        try {
            if (getFrameLocator().locator(locator).getAttribute(attribute).contains(expectedText)) {
                reportStep("The element with text : " + expectedText + " displayed as expected", "pass");
                return true;
            } else reportStep("The element with text : " + expectedText + " did not match", "warning");

        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        return false;

    }

    private boolean reportVisibleSuccess(String locator, String name) {
        getPage().locator(locator).scrollIntoViewIfNeeded();
        reportStep("The element : " + name + " displayed as expected", "pass");
        return true;
    }

    /**
     * Use this method to verify visibility of the element
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element field (label)
     * @return true if the element visible else false
     * @author Srikanth
     */
    public boolean verifyDisplayed(String locator, String name) {
        try {
            if (getPage().locator(locator).isVisible()) {
                return reportVisibleSuccess(locator, name);
            } else {
                pause("medium");
                if (getPage().isVisible(locator)) {
                    return reportVisibleSuccess(locator, name);
                }
            }
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        if (!getPage().isVisible(locator))
            reportStep("The element :" + name + " is not visible in the page", "warning");
        return false;
    }

    /**
     * Use this method to verify invisibility of the element
     *
     * @param locator The locator to identify the element
     * @param name    The name of the element field (label)
     * @return true if the element invisible else false
     * @author Srikanth
     */
    public boolean verifyNotDisplayed(String locator, String name) {
        try {
            if (!getPage().locator(locator).isVisible()) {
                reportStep("The element : " + name + " is not displayed as expected", "pass");
                return true;
            } else {
                pause("medium");
                reportStep("The element : " + name + " is not displayed as expected", "pass");
                return true;
            }
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        if (!getPage().locator(locator).isVisible())
            reportStep("The element : " + name + " is visible in the page", "warning");
        return false;
    }


    /**
     * Use this method to manage the wait between actions with sleep
     *
     * @param type The type of wait - allowed : low, medium, high
     * @author Srikanth
     */
    public void pause(String type) {
        try {
            switch (type.toLowerCase()) {
                case "low":
                    Thread.sleep(ConfigurationManager.configuration().pauseLow());
                    break;
                case "medium":
                    Thread.sleep(ConfigurationManager.configuration().pauseMedium());
                    break;
                case "high":
                    Thread.sleep(ConfigurationManager.configuration().pauseHigh());
                    break;
                default:
                    Thread.sleep(ConfigurationManager.configuration().pauseLow());
                    break;
            }
        } catch (Exception e) {
        }
    }


    /**
     * Use this method to reload the page
     *
     * @param locator - Any Selector
     * @author Srikanth
     */
    public void reLoadPage() {
        getPage().reload();
    }

    /**
     * Use this method to upload file
     *
     * @author Srikanth
     */
    public void uploadFile() {
        try {
            getPage().setInputFiles("input[type='file']", Path.of(attachment));
            waitForAppearance("//img[@data-testid='preview-thumbnail']");
            reportStep("File is Uploaded", "info");
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
    }

    public void uploadFile(String fileName) {
        try {
            getPage().setInputFiles("input[type='file']", Path.of("./src/test/resources/upload/" + fileName));
            /*waitForAppearance("//img[@data-testid='preview-thumbnail']");*/
            reportStep("File is Uploaded", "info");
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
        pause("medium");
    }

    public void importBudgetFile(String fileName) {
        try {
            getPage().setInputFiles("input[type='file']", Path.of("./src/test/resources/Import/" + fileName));
            /*waitForAppearance("//img[@data-testid='preview-thumbnail']");*/
            reportStep("File is Uploaded", "info");
        } catch (PlaywrightException e) {
            reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
        }
    }

    /**
     * Use this method to manage the wait for the element to load
     *
     * @param locator - Any Selector
     * @author Srikanth
     */
    public void waitForElementToLoad(String locator) {
        getPage().waitForSelector(locator, new Page.WaitForSelectorOptions().setTimeout(timeOut));
    }

    /**
     * Use this method to random number between the given range
     *
     * @param start
     * @param end
     * @return
     */
    public static int getRandomIntNumber(int start, int end) {
        Random ran = new Random();
        int result = ran.nextInt((end - start) + 1) + 1;
        return result;
    }

    /**
     * This function is used to get the date in MMM YYYY format (eg. Feb 2024)
     *
     * @param date - Pass a date in format DD/MM/YYYY (20/02/2024)
     * @return - It will return the string "Feb 2024"
     */
    public String getDateInMMMYYYYFormat(String date) {
        String outputDate = "";
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMM yyyy");

            Date inputDate = inputDateFormat.parse(date);
            outputDate = outputDateFormat.format(inputDate);
        } catch (ParseException e) {
            System.out.println("Error parsing the date: " + e.getMessage());
        }
        return outputDate;
    }

    /**
     * This method is used to round the double value
     *
     * @param value 12.3467777
     * @return 12.35 in double
     */
    public static double getDoubleRoundedValue(double value) {
        String roundedValue = String.format("%.2f", value);
        double roundedDouble = Double.parseDouble(roundedValue);
        System.out.println("Rounded value: " + roundedDouble);

        return roundedDouble;
    }

    /**
     * This method will take snapshot in base64 format
     *
     * @author Srikanth
     */
    @Override
    public String takeSnap() {
        return new String(Base64.getEncoder().encode(getPage().screenshot()));
    }

    /**
     * Performs a soft assertion for comparing two strings.
     * <p>
     * A "soft" assert allows the test to continue even after a failure,
     * <p>
     * collecting all failures and reporting them at the end of the test.
     *
     * @param actual   The actual string value to be compared.
     * @param expected The expected string value for comparison.
     */

    public void softAssertionString(String actual, String expected) {

        SoftAssert soft = new SoftAssert();

        soft.assertEquals(actual, expected);

        soft.assertAll();

        reportPass("Actual - " + actual + " and expected value - " + expected + " matches");

    }

    /**
     * Performs a soft assertion for comparing two strings.
     * <p>
     * A "soft" assert allows the test to continue even after a failure,
     * <p>
     * collecting all failures and reporting them at the end of the test.
     *
     * @param actual   The actual boolean value to be compared.
     * @param expected The expected boolean value for comparison.
     */

    public void softAssertionBoolean(Boolean actual, Boolean expected) {

        SoftAssert soft = new SoftAssert();

        soft.assertEquals(actual, expected);

        soft.assertAll();

        reportPass("Actual - " + actual + " and expected value - " + expected + " matches");

    }

    /**
     * Performs a hard assertion for comparing two strings.
     * <p>
     * A "hard" assert allows the test to fail and stop executing,
     *
     * @param actual   The actual string value to be compared.
     * @param expected The expected string value for comparison.
     */

    public void hardAssertionString(String actual, String expected) {

        Assert.assertEquals(actual, expected);

    }

    /**
     * Performs a hard assertion for comparing two boolean.
     * <p>
     * A "hard" assert allows the test to fail and stop executing,
     *
     * @param actual   The actual boolean value to be compared.
     * @param expected The expected boolean value for comparison.
     */

    public void hardAssertionBoolean(Boolean actual, Boolean expected) {

        Assert.assertEquals(actual, expected);

    }

    /**
     * Performs a hard assertion .
     * <p>
     * A "hard" assert allows the test to fail and stop executing,
     * <p>
     * <p>
     * <p>
     * * @param expected The expected boolean value for comparison.
     * <p>
     * Test will be pass only input is true
     */

    public void hardAssertionTrue(Boolean result) {

        Assert.assertTrue(result);

    }
}
