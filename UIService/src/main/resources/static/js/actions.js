function redirectToWeek() {
    const weekInput = document.getElementById('weekPicker').value;
    if (!weekInput) return;

    const [year, week] = weekInput.split('-W');
    const firstDayOfWeek = getFirstDayOfWeek(year, week);

    window.location.href = `?year=${firstDayOfWeek.getFullYear()}&month=${firstDayOfWeek.getMonth() + 1}&day=${firstDayOfWeek.getDate()}`;
}

function getFirstDayOfWeek(year, week) {
    const simple = new Date(year, 0, 1 + (week - 1) * 7);
    const dow = simple.getDay();
    const ISOweekStart = simple;
    if (dow <= 4)
        ISOweekStart.setDate(simple.getDate() - simple.getDay() + 1);
    else
        ISOweekStart.setDate(simple.getDate() + 8 - simple.getDay());
    return ISOweekStart;
}
