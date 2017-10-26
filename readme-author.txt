Všechny úlohy zadání považuji za implementované.
Výjimku možná tvoří bod 5.iii. Pokud hodnota CSV souboru obalená uvozovkami obsahuje lichý počet escapnutých dvojitých uvozovek, tak se řádek nezparsuje správně. Tedy např. "Jan\" Novák" -> chyba, ale "Jan \"Novák\"" -> OK. Dle zadání by to asi mělo zvládnout i lichý počet escapnutých uvozovek, na druhou stranu neznám validní řetězec, který by mohl obsahovat takovýto počet dvojitých uvozovek. Případná oprava by samozřejmě spočívala v přepsání regexu, kterým se řádek parsuje.

Poznámky k implementaci:
1) Aplikace má na můj vkus momentálně jednu vrstvu navíc (zbytečnou) a to vrstvu core/service - tedy např. IPAddressServiceImpl a LocationServiceImpl. Ideálně bych se této vrstvy úplně zbavil a controller by volal přímo mnou vytvořené servisy (které jsem pro rozlišení dal do package business). Mapování z databázových objektů na api objekty by pak mohlo probíhat přímo v controllerech. Tedy za mě ideální flow by bylo controller -> business service -> dao -> db. Udělal bych to tak, ale nechtěl jsem mazat předpřipravené servisy, ve kterých bylo “TODO implement me” :). Tento refactor by byl na 15 minut, případně jej mohu udělat.
Ta jedna vrsta navíc s sebou nese i jinou nepříjemnost a to kontrolu existence resourců na straně controlleru. Abych věděl, jestli nemám vrátit 404 tak musím prvně sáhnout do business servicy a pokud resource existuje, tak teprve volám vlastní logiku přes předpřipravené servisy. To je samozřejmě taky navíc, ideálně by stačil pouze jeden požadavek na servisní (business) vrstvu.

Další věc spojená s tímto je, že za mě by ideálně neměl controller implementovat žádná rozhraní a metody by měly vracet objekty typu ResponseEntity. Nechtěl jsem sahat do předpřipravené šablony, ale zase je to refactor na pár minut - případně mohu udělat.


2) S rozsahy IP adres pracuji tak, jak je uvedeno v zadání, tedy <ip_from; ip_to). Zde nevím, proč je ip_to exclusive. Typicky je to v datech broadcast (?.?.?.255). Např. dotaz na ip 62.168.40.254 tedy správně ještě vrátí Prahu, ale 62.168.40.255 už ne.

3) Traffic filter funguje pouze pro ipv4 adresy a samozřejmě navíc pouze ty, které jsou uložené v DB. IP adresy, které nezná, tak prostě propustí dále. Ze zadání nebylo zřejmé, jak se k nim zachovat, klidně by se daly i zaříznout. Obecně nejsem s implementací Filtru moc spokojený - nelíbí se mi ten synchronized block. Nicméně vhledem k tomu, že je nutné kontrolovat překročení limitu v rámci města, regionu i země tak jsem zatím nepřišel na lepší řešení. Pokud by stačilo kontrolovat pouze jednu hodnotu, tak by pochopitelně stačilo mít ConcurrentHashMapu a v ní jako hodnoty AtomicLong. Žádný synchronized blok by pak nebyl potřeba. Nyní je ale nutné prvně zjistit, jestli není alespoň jeden limit překročen a teprve poté je možné request propustit a inkrementovat počet úspěšných requestů. 
Pokud na toto máte elegantnější řešení, rád se s ním seznámím.

4) Import CSV souboru momentálně vyžaduje uložení objektů country, region a city v paměti. Pro velké datasety (miliony řádků) se obávám OutOfMemoryException. Bohužel jméno regionu ani města nemusí být předpokládám unikátní (ani v rámci datasetu), takže je nutné si tu příslušnost města k již existujícímu regionu pamatovat. Pokud by byla potřeba importovat velká data a toto řešení by nestačilo, bylo by nutné nic nedržet v paměti ale pro každý řádek se dotázat, jestli již náhodou neexistuje toto město v daném regionu v dané zemi. Nyní mi to přišlo jako overkill řešení.


