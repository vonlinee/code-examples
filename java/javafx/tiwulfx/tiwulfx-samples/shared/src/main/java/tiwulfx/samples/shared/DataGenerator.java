/*
 * Copyright (c) 2015, Panemu ( http://www.panemu.com/ )
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package tiwulfx.samples.shared;

import tiwulfx.samples.shared.dao.DaoBase;
import tiwulfx.samples.shared.pojo.Insurance;
import tiwulfx.samples.shared.pojo.Person;

import java.io.Serializable;
import java.util.*;

public class DataGenerator implements
        Serializable {
    private static int index = 0;

    public static void createWithTestData(int count) {
        final String[] fnames = {"Peter", "Alice", "Joshua", "Mike", "Olivia",
                "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
                "Lisa", "Marge"};
        final String[] lnames = {"Smith", "Gordon", "Simpson", "Brown",
                "Clavel", "Simons", "Verne", "Scott", "Allison", "Gates",
                "Rowling", "Barks", "Ross", "Schneider", "Tate"};

        final String streets[] = {"4215 Blandit Av.", "452-8121 Sem Ave",
                "279-4475 Tellus Road", "4062 Libero. Av.", "7081 Pede. Ave",
                "6800 Aliquet St.", "P.O. Box 298, 9401 Mauris St.",
                "161-7279 Augue Ave", "P.O. Box 496, 1390 Sagittis. Rd.",
                "448-8295 Mi Avenue", "6419 Non Av.",
                "659-2538 Elementum Street", "2205 Quis St.",
                "252-5213 Tincidunt St.", "P.O. Box 175, 4049 Adipiscing Rd.",
                "3217 Nam Ave", "P.O. Box 859, 7661 Auctor St.",
                "2873 Nonummy Av.", "7342 Mi, Avenue",
                "539-3914 Dignissim. Rd.", "539-3675 Magna Avenue",
                "Ap #357-5640 Pharetra Avenue", "416-2983 Posuere Rd.",
                "141-1287 Adipiscing Avenue", "Ap #781-3145 Gravida St.",
                "6897 Suscipit Rd.", "8336 Purus Avenue", "2603 Bibendum. Av.",
                "2870 Vestibulum St.", "Ap #722 Aenean Avenue",
                "446-968 Augue Ave", "1141 Ultricies Street",
                "Ap #992-5769 Nunc Street", "6690 Porttitor Avenue",
                "Ap #105-1700 Risus Street",
                "P.O. Box 532, 3225 Lacus. Avenue", "736 Metus Street",
                "414-1417 Fringilla Street", "Ap #183-928 Scelerisque Road",
                "561-9262 Iaculis Avenue"};
        if (lstInsurance.isEmpty()) {
            generateInsuranceData();
        }
        DaoBase<Person> daoPerson = new DaoBase<>(Person.class);
        Random r = new Random(0);

        for (int i = 0; i < count; i++) {
            index++;
            Person p = new Person();
            p.setName(fnames[r.nextInt(fnames.length)] + " " + lnames[r.nextInt(lnames.length)]);
            p.setAlive(index % 6 == 0 ? null : index % 7 == 0 ? false : true);
            p.setGender(index % 2 == 0 ? 'm' : 'f');
            p.setBirthDate(getDate(1990 + index, index % 12, index % 28 + 1));
            p.setVisit(index % 20 * 200);
            p.setWeight(30 + (index / 10d));
            p.setEmail(p.getName().toLowerCase().replace(' ', '.') + "_" + index + "@panemu.com");
            p.setBirthPlace(index % 7 != 0 ? birthPlaces[(index - 1) % birthPlaces.length] : null);

            int n = r.nextInt(100000);
            if (n < 10000) {
                n += 10000;
            }
            p.setInsurance(lstInsurance.get((index - 1) % (lstInsurance.size())));

            /**
             * Since inserting all persons in one transaction disregards the order,
             * let's just insert them one by one
             */
            daoPerson.insert(p);
        }
    }

    private static Date getDate(int year, int month, int day) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public static final String birthPlaces[] = {"Paris", "Rome", "Semarang", "Surabaya", "Stockholm", "Tokyo", "Turku", "Yogyakarta", "Amsterdam", "Bali", "Bandung", "Berlin", "Helsinki",
            "Hong Kong", "London", "Luxemburg", "New York", "Oslo"};

    private static void generateInsuranceData() {

        DaoBase<Insurance> daoInsurance = new DaoBase<>(Insurance.class);
        for (int i = 1; i <= 15; i++) {
            Insurance insurance = new Insurance(null, "" + i + i + i, "Insurance Package " + i);
            insurance = daoInsurance.insert(insurance);
            lstInsurance.add(insurance);
        }
    }

    private static List<Insurance> lstInsurance = new ArrayList<>();
}
