create table user_list(
    id serial primary key not null,
    full_name varchar(60) not null,
    e_mail varchar(120),
    password varchar(15)
);

create table account (
  id serial primary key not null,
  user_list_id int not null references user(id),
  balance money not null
);

create table transaction (
  id serial primary key not null,
  account_id int not null references account(id),
  sum money not null,
  date date not null,
  categorie_id int not null references categorie(id)
);

create table categorie (
  id serial primary key not null,
  description varchar(25) not null
);



<mxfile userAgent="Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36 OPR/56.0.3051.104" version="9.4.6" editor="www.draw.io" type="device"><diagram id="426afcef-643a-c1e4-72c8-1b3eb48c9bad" name="Страница 1">7Vvfb9s2EP5r/LjB1K8oj7OXdg8pUCwD1j0FjERLXClRoKjY7l+/o0RaVugVai0rBszEQMTjSSS/7+7IO8ULf13sPgpc5Z94StjCW6a7hf/7wvOQH0fwR0n2neQuXnaCTNBUK/WCJ/qNaKFRa2hK6oGi5JxJWg2FCS9LksiBDAvBt0O1DWfDUSucEUvwlGBmS/+mqcw7aezd9fI/CM1yMzKK7rueAhtlvZI6xynfHon8h4W/FpzL7qrYrQlT4Blcuvs+/E/vYWKClHLMDWF3wytmjV5bUxOh5yb3ZsH1lhYMl9BabXgpn3TPEtpJTln6iPe8UQPWEidfTWuVc0G/gT5m0IVAAN1Caj69SD2NMrbmjIt2HJ8s1e/gzif1RD2WIDXc+9msDr0RfcK7geIjrqWZJWcMVzV9aeetbiywyGi54lLyQiuZVX4YTmrT/kA/ZjQrQZbAWACSxqJbDQqgbaOvCXklQpLdkUiz8ZHwgkixBxXdG2jD2A+b297MUKRl+bGJhYE2b23a2eHJPftwoQ3gtDHcWcYAPW9NAdYhWyYF/0oMSiXvbOMIOC2qK5zQMnskGzXToJf8qSevRBzw2bDWD3KapqRUFHKJJX452FnFaSnbxYUr+AAG6+Wv4SKEOa2hjfo2fJS6kGtewjQxbckgYAxbogxiQJt3krZwNG2aJy8ax5PRO4cmP7B4slhitEO/ZcnEKHSaImPWrOVopRZNIdb9psUFMMKOqPyLVyD9BVnk+ja5/gkiGX4h7DOvqaRcPV90um8IfgcOQ28ch/H5FMYWg5uGsecSF8Q53CiyopGBcQqHC53DXYTDeGTQnMDhkGdRSJ4LTJlzt1FU3Qczupt9KHXuNgGHyBsZM6fwN9/isMJ1veXCnSjHshXP53LI3uFwkvAGZu9SwfdKBf3oJ3NB4+dnWURkWcStJoMH57jKbDBwu+VlSJwxHUSny3DPzuOuMR0MXDp4GRLnzAftWucLhg0/udH6y4+TNWdGiOx6GSyzrHHS2rA7ol7LEXX0yQfFUzjxvTujHvC86jOqXX1zO+YUJM54RjVJpV0iuN1j6g/zNesxFVmEOaebgsQZj6mevcPVTeG87fqOqIEdHp23TUHinG8tPDtkpljeakr4E1TN+MrCt0Ojc7hJWAxHhs0pHM5O7BNwuAzyaeKOleMpi0bmAVM4nmeXPw+cWYS5UsxcpZjIG5Zi/HBkMI6nsAn7Bcateq931W8LfTvgul1zChLnrMTY7y5SUieCVier4c7r3r0UEyKLFZJmxOyGsPKcZ7zE7KGXAp5NmZJUo5nLwuyFsGix/6LlbeMf1QAs/dW/RMq9hgo3kiuuDk9/5K37oe9tODVvRKLnqO0Mtt6MGC0d6NX0v4uyIAxL+koGDz8LQztQnYPhjsovR9c9ggd00YXRNf9hcwyv2cPfAV5/Zngvbbwm0g7gDWaCF5r9d7javqNvwvkP/wE=</diagram></mxfile>