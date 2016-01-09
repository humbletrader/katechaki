/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine.out;

public final class PageSize {

    public final static PageSize A0_PORTRAIT = new PageSize(1189, 841);
    public final static PageSize A0_LANDSCAPE = new PageSize(841, 1189);

    public final static PageSize A1_PORTRAIT = new PageSize(841, 594);
    public final static PageSize A1_LANDSCAPE = new PageSize(594, 841);

    public final static PageSize A2_PORTRAIT = new PageSize(594, 420);
    public final static PageSize A2_LANDSCAPE = new PageSize(420, 594);

    /**
     * master-name="A3" page-height="594mm" page-width="420mm"
     */
    public final static PageSize A3_PORTRAIT = new PageSize(420, 297);
    public final static PageSize A3_LANDSCAPE = new PageSize(297, 420);

    /**
     * master-name="A4" page-width="297mm" page-height="210mm"
     */
    public final static PageSize A4_PORTRAIT = new PageSize(297, 210);
    public final static PageSize A4_LANDSCAPE = new PageSize(210, 297);

    public final static PageSize A5_PORTRAIT = new PageSize(210, 148);
    public final static PageSize A5_LANDSCAPE = new PageSize(148, 210);

    public final static PageSize A6_PORTRAIT = new PageSize(148, 105);
    public final static PageSize A6_LANDSCAPE = new PageSize(105, 148);

    public final static PageSize A7_PORTRAIT = new PageSize(105, 74);
    public final static PageSize A7_LANDSCAPE = new PageSize(74, 105);

    public final static PageSize A8_PORTRAIT = new PageSize(74, 52);
    public final static PageSize A8_LANDSCAPE = new PageSize(52, 74);

    private int pageHeightInMilimeters;
    private int pageWidthInMilimeters;

    /**
     * 
     * @param pageHeight
     * @param pageWidthInCm
     */
    public PageSize(int pageHeight, int pageWidth) {
        this.pageHeightInMilimeters = pageHeight;
        this.pageWidthInMilimeters = pageWidth;
    }

    public double getHeight() {
        return pageHeightInMilimeters;
    }

    public double getWidth() {
        return pageWidthInMilimeters;
    }
}
