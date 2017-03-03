/**

 * �������η����߲�ֵ��.(�������η�����������)

 */

public class EaseCubicInterpolator implements Interpolator {



    private final static int ACCURACY = 4096;

    private int mLastI = 0;

    private final PointF mControlPoint1 = new PointF();

    private final PointF mControlPoint2 = new PointF();



    /**

     * �����м��������Ƶ�.<br>

     * ���߹���: http://cubic-bezier.com/<br>

     * 

     * @param x1

     * @param y1

     * @param x2

     * @param y2

     */

    public EaseCubicInterpolator(float x1, float y1, float x2, float y2) {

        mControlPoint1.x = x1;

        mControlPoint1.y = y1;

        mControlPoint2.x = x2;

        mControlPoint2.y = y2;

    }



    @Override

    public float getInterpolation(float input) {

        float t = input;

        // �������t��ֵ[0,1]

        for (int i = mLastI; i < ACCURACY; i++) {

            t = 1.0f * i / ACCURACY;

            double x = cubicCurves(t, 0, mControlPoint1.x, mControlPoint2.x, 1);

            if (x >= input) {

                mLastI = i;

                break;

            }

        }

        double value = cubicCurves(t, 0, mControlPoint1.y, mControlPoint2.y, 1);

        if (value > 0.999d) {

            value = 1;

            mLastI = 0;

        }

        return (float) value;

    }



    /**

     * �����α���������(�ĸ����Ƶ�)һ����ĳ��ά�ȵ�ֵ.<br>

     * �ο�����: <em> http://devmag.org.za/2011/04/05/bzier-curves-a-tutorial/ </em>

     * 

     * @param t

     *            ȡֵ[0, 1]

     * @param value0

     * @param value1

     * @param value2

     * @param value3

     * @return

     */

    public static double cubicCurves(double t, double value0, double value1,

            double value2, double value3) {

        double value;

        double u = 1 - t;

        double tt = t * t;

        double uu = u * u;

        double uuu = uu * u;

        double ttt = tt * t;



        value = uuu * value0;

        value += 3 * uu * t * value1;

        value += 3 * u * tt * value2;

        value += ttt * value3;

        return value;

    }

}